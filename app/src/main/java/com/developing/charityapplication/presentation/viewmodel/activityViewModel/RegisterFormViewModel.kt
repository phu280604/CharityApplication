package com.developing.charityapplication.presentation.viewmodel.activityViewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.usecase.validation.ValidateEmail
import com.developing.charityapplication.domain.usecase.validation.ValidateName
import com.developing.charityapplication.domain.usecase.validation.ValidatePassword
import com.developing.charityapplication.domain.usecase.validation.ValidateRepeatedPassword
import com.developing.charityapplication.domain.usecase.validation.ValidateUsername
import com.developing.charityapplication.presentation.event.activityEvent.RegisterFormEvent
import com.developing.charityapplication.presentation.state.activityState.RegisterFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterFormViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    // endregion

    // region --- Methods ---

    fun onEvent(event: RegisterFormEvent){
        when(event){
            is RegisterFormEvent.FirstNameChange -> {
                _state = _state.copy(firstName = event.firstName)
                _state = _state.copy(firstNameError = null)
            }
            is RegisterFormEvent.LastNameChange -> {
                _state = _state.copy(lastName = event.lastName)
                _state = _state.copy(lastNameError = null)
            }
            is RegisterFormEvent.UsernameChange -> {
                _state = _state.copy(username = event.username)
                _state = _state.copy(usernameError = null)
            }
            is RegisterFormEvent.EmailChange -> {
                _state = _state.copy(email = event.email)
                _state = _state.copy(emailError = null)
            }
            is RegisterFormEvent.PasswordChange -> {
                _state = _state.copy(password = event.password)
                _state = _state.copy(passwordError = null)
            }
            is RegisterFormEvent.RepeatedPasswordChange -> {
                _state = _state.copy(repeatedPassword = event.repeatedPassword)
                _state = _state.copy(repeatedPasswordError = null)
            }
            is RegisterFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val firstName = ValidateName().execute(_state.firstName)
        val lastName = ValidateName().execute(_state.lastName)
        val username = ValidateUsername().execute(_state.username)
        val email = ValidateEmail().execute(_state.email)
        val password = ValidatePassword().execute(_state.password)
        val repeatedPassword = ValidateRepeatedPassword().execute(_state.password, _state.repeatedPassword)

        val hasError = listOf(
            firstName,
            lastName,
            username,
            email,
            password,
            repeatedPassword
        ).any { !it.successful }

        if (hasError){
            _state = _state.copy(
                firstNameError = firstName.errorMessage,
                lastNameError = lastName.errorMessage,
                usernameError = username.errorMessage,
                emailError = email.errorMessage,
                passwordError = password.errorMessage,
                repeatedPasswordError = repeatedPassword.errorMessage,
            )
            return
        }

        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    // endregion

    // region --- Properties ---

    val state: RegisterFormState
        get() = _state

    // endregion

    // region --- Fields ---

    private var _state by mutableStateOf(RegisterFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion

}