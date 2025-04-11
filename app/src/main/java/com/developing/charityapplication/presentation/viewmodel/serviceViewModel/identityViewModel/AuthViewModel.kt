package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: IAuthRepo
): ViewModel() {
    // region --- Methods ---

    fun defaultLogin(loginInfo: RequestLoginM) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.defaultLogin(loginInfo)
                _loginResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    // endregion

    // region --- Properties ---

    val loginResponse: StateFlow<ResponseM<Result>?>
        get() = _loginResponse

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    // endregion

    // region --- Fields ---

    private val _loginResponse = MutableStateFlow<ResponseM<Result>?>(null)
    private val _isLoading = MutableStateFlow(false)

    // endregion
}