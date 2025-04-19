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
import com.developing.charityapplication.domain.model.postModel.ResponsePostsByProfileId
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.domain.repoInter.postsRepoInter.IPostRepo
import com.developing.charityapplication.domain.repoInter.profileRepoInter.IProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repo: IPostRepo
): ViewModel() {

    // region --- Methods ---

    fun getAllPosts(profileId: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.getAllPosts()
                result?.result = result.result?.filter { it.profileId != profileId }
                _allPostsResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getPostsByProfileId(profileId: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.getPostsByProfileId(profileId)
                _postsResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createPost(
        postRequest: RequestPostContentM,
        files: List<MultipartBody.Part>
    ){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("profileId", "CreatingPost: ${postRequest.profileId}")
                Log.d("ImagesSave", "Multi: ${files.size}")
                val result = repo.createPost(postRequest, files)
                _postResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
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
            _isLoading.value = true
            try {
                Log.d("profileId", "CreatingPost: ${postRequest.profileId}")
                Log.d("ImagesSave", "Multi: ${files?.size}")
                val result = repo.updatePost(postId, filesToRemove, postRequest, files)
                _postResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePost(
        postId: String
    ){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.deletePost(postId)
                _postDeletedResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetResponse(){
        _postDeletedResponse.value = null
    }

    // endregion

    // region --- Properties ---

    val allPostsResponse: StateFlow<ResponseM<List<ResponsePostM>>?>
        get() = _allPostsResponse

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

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    // endregion

    // region --- Fields ---

    private val _allPostsResponse = MutableStateFlow<ResponseM<List<ResponsePostM>>?>(null)
    private val _postResponse = MutableStateFlow<ResponseM<ResponsePostM>?>(null)
    private val _postDeletedResponse = MutableStateFlow<ResponseM<String>?>(null)
    private val _postsResponse = MutableStateFlow<ResponseM<ResponsePostsByProfileId>?>(null)
    private val _profileResponse = MutableStateFlow<ResponseM<ResponseProfilesM>?>(null)
    private val _activeProfileResponse = MutableStateFlow<ResponseM<String>?>(null)
    private val _isLoading = MutableStateFlow(false)

    // endregion

}