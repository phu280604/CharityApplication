package com.developing.charityapplication.presentation.viewmodel.activityViewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.usecase.validation.ValidateEmail
import com.developing.charityapplication.domain.usecase.validation.ValidatePassword
import com.developing.charityapplication.domain.usecase.validation.ValidateUsername
import com.developing.charityapplication.presentation.event.activityEvent.EmailFormEvent
import com.developing.charityapplication.presentation.event.activityEvent.LoginFormEvent
import com.developing.charityapplication.presentation.state.activityState.EmailFormState
import com.developing.charityapplication.presentation.state.activityState.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmailFormViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    //endregion

    // region --- Methods ---

    fun onEvent(event: EmailFormEvent){
        when(event){
            is EmailFormEvent.EmailChange -> {
                _state.value = _state.value.copy(email = event.email)
                _state.value = _state.value.copy(emailError = null)
            }
            is EmailFormEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val email = ValidateEmail().execute(_state.value.email)

        val hasError = listOf(
            email
        ).any { !it.successful }

        if (hasError){
            _state.value = _state.value.copy(
                emailError = email.errorMessage,
            )
            return
        }

        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun resetData(){
        _state.value = _state.value.copy(
            email = "",
            emailError = null
        )
    }

    // endregion

    // region --- Properties ---

    val state: StateFlow<EmailFormState>
        get() = _state

    // endregion

    // region --- Fields ---

    private var _state = MutableStateFlow(EmailFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion
}