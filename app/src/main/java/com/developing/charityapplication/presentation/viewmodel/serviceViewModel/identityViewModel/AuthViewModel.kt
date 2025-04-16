package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.data.authentication.TokenProvider
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.RegisterFormViewModel.ValidationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: IAuthRepo,
    private val tokenProvider: TokenProvider
): ViewModel() {
    // region --- Methods ---

    fun defaultLogin(loginInfo: RequestLoginM) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.defaultLogin(loginInfo)
                _loginResponse.value = result
                val token = _loginResponse.value?.result?.token
                if (!token.isNullOrEmpty())
                    tokenProvider.saveToken(token)

                Log.d("TokenAPI", token.toString())
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun sendOtp_Email(email: RequestEmailM) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repo.sendOtp_email(email)
                _sendingOtpResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun verifyOtp_Email(otp: RequestOTPM, loginInfo: RequestLoginM) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val verifyRes = repo.verifyOtp_email(otp)
                _verifyingOtpResponse.value = verifyRes

                if (!verifyRes?.result?.result.isNullOrEmpty()){
                    val result = repo.defaultLogin(loginInfo)
                    _loginResponse.value = result
                    val token = _loginResponse.value?.result?.token
                    if (!token.isNullOrEmpty())
                        tokenProvider.saveToken(token)

                    Log.d("TokenAPI", token.toString())
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

    val loginResponse: StateFlow<ResponseM<Result>?>
        get() = _loginResponse

    val sendingOtpResponse: StateFlow<ResponseM<ResultM>?>
        get() = _sendingOtpResponse

    val verifyingOtpResponse: StateFlow<ResponseM<ResultM>?>
        get() = _verifyingOtpResponse

    val isLoading: StateFlow<Boolean>
        get() = _isLoading

    // endregion

    // region --- Fields ---

    private val _loginResponse = MutableStateFlow<ResponseM<Result>?>(null)
    private val _sendingOtpResponse = MutableStateFlow<ResponseM<ResultM>?>(null)
    private val _verifyingOtpResponse = MutableStateFlow<ResponseM<ResultM>?>(null)
    private val _isLoading = MutableStateFlow(false)

    // endregion
}