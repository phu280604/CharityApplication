package com.developing.charityapplication.presentation.viewmodel.activityViewModel

import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import com.developing.charityapplication.presentation.event.activityEvent.AuthChangeEvent
import com.developing.charityapplication.presentation.state.activityState.AuthenticationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(): ViewModel() {

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

    fun onSubmit() {
        // TODO: Xử lý logic xác thực
    }

    fun setState(amount: Int) {
        _state = MutableStateFlow(List(amount) { AuthenticationState()})

        _focusRequest = MutableStateFlow(List(amount) { FocusRequester()})
    }

    // endregion

    // region --- Properties ---

    val state: StateFlow<List<AuthenticationState>>
        get() = _state

    val focusRequest: StateFlow<List<FocusRequester>>
        get() = _focusRequest

    // endregion

    // region --- Fields ---

    private var _state = MutableStateFlow(List(5) { AuthenticationState()})

    private var _focusRequest = MutableStateFlow(List(5) { FocusRequester()})

    // endregion
}
