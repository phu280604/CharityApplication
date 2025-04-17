package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.profileViewModel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.data.authentication.TokenProvider
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.repoInter.profileRepoInter.IProfileRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repo: IProfileRepo
): ViewModel() {

    // region --- Methods ---

    fun getProfileByProfileId(profileId: String){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.getProfileByProfileId(profileId)
                _profileResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateProfile(
        profileId: String,
        profileInfo: RequestUpdateProfileM,
        avatar: MultipartBody.Part?
    ){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("Json", "newPro: ${profileId}")
                Log.d("Json", "newPro: ${profileInfo}")
                Log.d("Json", "newPro: ${avatar}")
                val result = repo.updateProfile(profileId, profileInfo, avatar)
                _profileResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun setActiveProfile(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _profilesResponse.value = repo.getProfile()
                val profileId = _profilesResponse.value?.result?.firstOrNull()?.profileId

                if (!profileId.isNullOrEmpty()){
                    val result = repo.setActiveProfile(profileId)
                    _activeProfileResponse.value = result
                }
                else{
                    Log.d("ActiveProfile", "hello")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // endregion

    // region --- Properties ---

    val profilesResponse: StateFlow<ResponseM<List<ResponseProfilesM>>?>
        get() = _profilesResponse

    val profileResponse: StateFlow<ResponseM<ResponseProfilesM>?>
        get() = _profileResponse

    val activeProfileResponse: StateFlow<ResponseM<String>?>
        get() = _activeProfileResponse

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    // endregion

    // region --- Fields ---

    private val _profilesResponse = MutableStateFlow<ResponseM<List<ResponseProfilesM>>?>(null)
    private val _profileResponse = MutableStateFlow<ResponseM<ResponseProfilesM>?>(null)
    private val _activeProfileResponse = MutableStateFlow<ResponseM<String>?>(null)
    private val _isLoading = MutableStateFlow(false)

    // endregion

}