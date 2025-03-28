package com.developing.charityapplication.presentation.event.activityEvent

sealed class RegisterFormEvent {
    data class FirstNameChange(val firstName: String): RegisterFormEvent()
    data class LastNameChange(val lastName: String): RegisterFormEvent()
    data class UsernameChange(val Username: String): RegisterFormEvent()
    data class EmailChange(val email: String): RegisterFormEvent()
    data class PasswordChange(val password: String): RegisterFormEvent()
    data class RepeatedPasswordChange(val repeatedPassword: String): RegisterFormEvent()

    object Submit: RegisterFormEvent()
}