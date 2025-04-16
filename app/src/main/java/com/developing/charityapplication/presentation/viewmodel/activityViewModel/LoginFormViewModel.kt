package com.developing.charityapplication.presentation.viewmodel.activityViewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.usecase.validation.ValidatePassword
import com.developing.charityapplication.domain.usecase.validation.ValidateUsername
import com.developing.charityapplication.presentation.event.activityEvent.LoginFormEvent
import com.developing.charityapplication.presentation.state.activityState.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginFormViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    //endregion

    // region --- Methods ---

    fun onEvent(event: LoginFormEvent){
        when(event){
            is LoginFormEvent.usernameChange -> {
                _state = _state.copy(username = event.username)
                _state = _state.copy(usernameError = null)
            }
            is LoginFormEvent.passwordChange -> {
                _state = _state.copy(password = event.password)
                _state = _state.copy(passwordError = null)
            }
            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val username = ValidateUsername().execute(_state.username)
        val password = ValidatePassword().execute(_state.password)

        val hasError = listOf(
            username,
            password
        ).any { !it.successful }

        if (hasError){
            _state = _state.copy(
                usernameError = username.errorMessage,
                passwordError = password.errorMessage
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    // endregion

    // region --- Properties ---

    val state: LoginFormState
        get() = _state

    // endregion

    // region --- Fields ---

    private var _state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion
}