package com.developing.charityapplication.presentation.viewmodel.activityViewModel

import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.usecase.validation.ValidateOtp
import com.developing.charityapplication.presentation.event.activityEvent.AuthChangeEvent
import com.developing.charityapplication.presentation.state.activityState.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.developing.charityapplication.R

@HiltViewModel
class AuthenticationViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    // endregion

    // region --- Methods ---

    fun onEvent(index: Int, onChangeEvent: AuthChangeEvent) {
        when (onChangeEvent) {
            is AuthChangeEvent.PinValueChange -> {
                _state.value = _state.value.toMutableStateList().apply {
                    this[index] = this[index].copy(
                        pinValue = onChangeEvent.pinValue,
                        pinValueError = null
                    )
                }
            }
            is AuthChangeEvent.Submit -> {
                onSubmit()
            }
        }
    }

    fun onSubmit(){
        var otp: String = ""
        _state.value.forEach {
            otp += it.pinValue
        }
        val otpChecker = ValidateOtp().execute(otp)

        val hasError = listOf(
            otpChecker
        ).any { !it.successful }

        if (hasError){
            _checkingState.value = R.string.invalid_Otp
            return
        }

        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun resetState(){
        _checkingState.value = 0
    }

    // endregion

    // region --- Properties ---

    val state: StateFlow<List<AuthenticationState>>
        get() = _state

    val checkingState: StateFlow<Int>
        get() = _checkingState

    val focusRequest: StateFlow<List<FocusRequester>>
        get() = _focusRequest

    // endregion

    // region --- Fields ---

    private val _state = MutableStateFlow(List(6) { AuthenticationState() })
    private var _checkingState = MutableStateFlow(0)

    private val _focusRequest = MutableStateFlow(List(6) { FocusRequester()})

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion
}
