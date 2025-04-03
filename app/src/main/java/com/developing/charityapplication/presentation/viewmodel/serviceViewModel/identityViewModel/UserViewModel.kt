package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.model.identityModel.RequestCreateUser
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.identityModel.UserM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IUserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
   private val repo: IUserRepo
): ViewModel(){

    // region --- Methods ---

    fun createAccountUser(request: RequestCreateUser){
        viewModelScope.launch{
            _isLoading.value = true
            try {
                val result = repo.createAccount(request)
                _userInfo.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // endregion




    // region --- Properties ---

    val userInfo: StateFlow<ResponseM<UserM>?>
        get() = _userInfo

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    // endregion

    // region --- Fields ---

    private val _userInfo = MutableStateFlow<ResponseM<UserM>?>(null)

    private val _isLoading = MutableStateFlow(false)

    // endregion

}