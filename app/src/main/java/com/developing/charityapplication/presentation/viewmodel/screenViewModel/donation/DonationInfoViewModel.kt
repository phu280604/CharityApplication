package com.developing.charityapplication.presentation.viewmodel.screenViewModel.donation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.usecase.validation.ValidateAmount
import com.developing.charityapplication.domain.usecase.validation.ValidateName
import com.developing.charityapplication.domain.usecase.validation.ValidateUsername
import com.developing.charityapplication.infrastructure.utils.DownloadImage
import com.developing.charityapplication.presentation.event.screenEvent.DonationEvent
import com.developing.charityapplication.presentation.event.screenEvent.EditProfileEvent
import com.developing.charityapplication.presentation.state.screenState.DonationState
import com.developing.charityapplication.presentation.state.screenState.EditProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class DonationInfoViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    // endregion

    // region --- Methods ---

    fun onEvent(event: DonationEvent){
        when(event){
            is DonationEvent.ContentChange -> {
                _state.value = _state.value.copy(content = event.content)
            }
            is DonationEvent.AmountChange -> {
                _state.value = _state.value.copy(amount = event.amount)
                _state.value = _state.value.copy(amountError = null)
            }
            is DonationEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val amount = ValidateAmount().execute(_state.value.amount.toLong(), 20000)
        val hasError = listOf(
            amount,
        ).any { !it.successful }
        if (hasError){
            _state.value = _state.value.copy(
                amountError = amount.errorMessage
            )
            return
        }

        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    // endregion

    // region --- Properties ---

    val state: StateFlow<DonationState>
        get() = _state

    // endregion

    // region --- Fields ---

    private var _state = MutableStateFlow(DonationState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion

}