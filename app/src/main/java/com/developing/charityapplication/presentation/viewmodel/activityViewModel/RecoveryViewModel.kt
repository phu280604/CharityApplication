package com.developing.charityapplication.presentation.viewmodel.activityViewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.usecase.validation.ValidateEmail
import com.developing.charityapplication.domain.usecase.validation.ValidateName
import com.developing.charityapplication.domain.usecase.validation.ValidatePassword
import com.developing.charityapplication.domain.usecase.validation.ValidateRepeatedPassword
import com.developing.charityapplication.domain.usecase.validation.ValidateUsername
import com.developing.charityapplication.presentation.event.activityEvent.RecoveryEvent
import com.developing.charityapplication.presentation.event.activityEvent.RegisterFormEvent
import com.developing.charityapplication.presentation.state.activityState.RecoveryState
import com.developing.charityapplication.presentation.state.activityState.RegisterFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoveryViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    // endregion

    // region --- Methods ---

    fun onEvent(event: RecoveryEvent){
        when(event){
            is RecoveryEvent.PasswordChange -> {
                _state.value = _state.value.copy(password = event.password)
                _state.value = _state.value.copy(passwordError = null)
            }
            is RecoveryEvent.RepeatedPasswordChange -> {
                _state.value = _state.value.copy(repeatedPassword = event.repeatedPassword)
                _state.value = _state.value.copy(repeatedPasswordError = null)
            }
            is RecoveryEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val password = ValidatePassword().execute(_state.value.password)
        val repeatedPassword = ValidateRepeatedPassword().execute(_state.value.password, _state.value.repeatedPassword)

        val hasError = listOf(
            password,
            repeatedPassword
        ).any { !it.successful }

        if (hasError){
            _state.value = _state.value.copy(
                passwordError = password.errorMessage,
                repeatedPasswordError = repeatedPassword.errorMessage,
            )
            return
        }

        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun resetData(){
        _state.value = _state.value.copy(
            password = "",
            passwordError = null,
            repeatedPassword = "",
            repeatedPasswordError = null
        )
    }

    // endregion

    // region --- Properties ---

    val state: StateFlow<RecoveryState>
        get() = _state

    // endregion

    // region --- Fields ---

    private var _state = MutableStateFlow(RecoveryState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion

}