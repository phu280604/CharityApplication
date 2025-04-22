package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.postViewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.data.authentication.TokenProvider
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.postModel.RequestPostContentM
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.postModel.ResponsePosts
import com.developing.charityapplication.domain.model.postModel.ResponsePostsByProfileId
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.donationRepoInter.IDonationRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.domain.repoInter.postsRepoInter.IPostRepo
import com.developing.charityapplication.domain.repoInter.profileRepoInter.IProfileRepo
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.loading.LoadingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: IPostRepo,
    private val repoProfile: IProfileRepo,
    private val repoDonation: IDonationRepo
): ViewModel() {

    // region --- Methods ---

    fun getAllPosts(profileId: String){
        viewModelScope.launch {
            LoadingViewModel.enableLoading(true)
            try {
                val result = repo.getAllPosts()
                val selfProfile = repoProfile.getProfileByProfileId(profileId)
                val posts = result?.result?.data?.filter { it.profileId != profileId }
                val profilesId = posts?.mapNotNull { it.profileId }?.distinct()

                val profilesInfo = mutableListOf<ResponseProfilesM>()
                val totalDonations = mutableListOf<Pair<String, Int>>()

                if(!posts.isNullOrEmpty()){
                    posts.forEach{ post ->
                        val donationResult = repoDonation.getTotalDonateByPostId(post.id)
                        donationResult?.result?.let {
                            totalDonations.add(Pair(post.id, it))
                        }
                    }
                }

                if (!profilesId.isNullOrEmpty()) {
                    profilesId.forEach { id ->
                        val profileInfo = repoProfile.getProfileByProfileId(id)
                        profileInfo?.result?.let {
                            profilesInfo.add(it)
                        }
                    }
                }

                _allDonationsResponse.value = totalDonations
                _allProfilesResponse.value = profilesInfo
                _allPostsResponse.value = posts
                _selfProfileResponse.value = selfProfile?.result

            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    fun getPostsByProfileId(profileId: String){
        viewModelScope.launch {
            LoadingViewModel.enableLoading(true)
            try {
                val result = repo.getPostsByProfileId(profileId)
                _postsResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    fun createPost(
        postRequest: RequestPostContentM,
        files: List<MultipartBody.Part>
    ){
        viewModelScope.launch {
            LoadingViewModel.enableLoading(true)
            try {
                Log.d("profileId", "CreatingPost: ${postRequest.profileId}")
                Log.d("ImagesSave", "Multi: ${files.size}")
                val result = repo.createPost(postRequest, files)
                _postResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    fun updatePost(
        postId: String,
        filesToRemove: List<String>,
        postRequest: RequestPostContentM,
        files: List<MultipartBody.Part>?
    ){
        viewModelScope.launch {
            LoadingViewModel.enableLoading(true)
            try {
                Log.d("profileId", "CreatingPost: ${postRequest.profileId}")
                Log.d("ImagesSave", "Multi: ${files?.size}")
                val result = repo.updatePost(postId, filesToRemove, postRequest, files)
                _postResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    fun deletePost(
        postId: String,
        profileId: String
    ){
        viewModelScope.launch {
            LoadingViewModel.enableLoading(true)
            try {
                val result = repo.deletePost(postId)
                _postDeletedResponse.value = result

                val refreshPost = repo.getPostsByProfileId(profileId)
                _postsResponse.value = refreshPost
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    fun resetResponse(){
        _postDeletedResponse.value = null
    }

    // endregion

    // region --- Properties ---

    val allPostsResponse: StateFlow<List<ResponsePostM>?>
        get() = _allPostsResponse

    val allDonationsResponse: StateFlow<List<Pair<String, Int>>?>
        get() = _allDonationsResponse

    val allProfilesResponse: StateFlow<List<ResponseProfilesM>?>
        get() = _allProfilesResponse

    val selfProfilesResponse: StateFlow<ResponseProfilesM?>
        get() = _selfProfileResponse

    val postResponse: StateFlow<ResponseM<ResponsePostM>?>
        get() = _postResponse

    val postDeletedResponse: StateFlow<ResponseM<String>?>
        get() = _postDeletedResponse

    val postsResponse: StateFlow<ResponseM<ResponsePostsByProfileId>?>
        get() = _postsResponse

    val profileResponse: StateFlow<ResponseM<ResponseProfilesM>?>
        get() = _profileResponse

    val activeProfileResponse: StateFlow<ResponseM<String>?>
        get() = _activeProfileResponse

    // endregion

    // region --- Fields ---

    private val _allPostsResponse = MutableStateFlow<List<ResponsePostM>?>(null)
    private val _allDonationsResponse = MutableStateFlow<List<Pair<String, Int>>?>(null)
    private val _allProfilesResponse = MutableStateFlow<List<ResponseProfilesM>?>(null)
    private val _selfProfileResponse = MutableStateFlow<ResponseProfilesM?>(null)
    private val _postResponse = MutableStateFlow<ResponseM<ResponsePostM>?>(null)
    private val _postDeletedResponse = MutableStateFlow<ResponseM<String>?>(null)
    private val _postsResponse = MutableStateFlow<ResponseM<ResponsePostsByProfileId>?>(null)
    private val _profileResponse = MutableStateFlow<ResponseM<ResponseProfilesM>?>(null)
    private val _activeProfileResponse = MutableStateFlow<ResponseM<String>?>(null)

    // endregion

}