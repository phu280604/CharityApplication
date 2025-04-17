package com.developing.charityapplication.presentation.viewmodel.screenViewModel.creatingPost

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.usecase.validation.ValidateName
import com.developing.charityapplication.domain.usecase.validation.ValidateUsername
import com.developing.charityapplication.infrastructure.utils.DownloadImage
import com.developing.charityapplication.presentation.event.screenEvent.CreatingPostEvent
import com.developing.charityapplication.presentation.event.screenEvent.EditProfileEvent
import com.developing.charityapplication.presentation.state.screenState.CreatingPostState
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
class CreatingPostViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    // endregion

    // region --- Methods ---

    fun onEvent(event: CreatingPostEvent){
        when(event){
            is CreatingPostEvent.ContentChange -> {
                _state.value = _state.value.copy(content = event.content)
                _state.value = _state.value.copy(contentError = null)
            }
            is CreatingPostEvent.StartDateChange -> {
                _state.value = _state.value.copy(startDate = event.startDate)
                _state.value = _state.value.copy(startDateError = null)
            }
            is CreatingPostEvent.EndDateChange -> {
                _state.value = _state.value.copy(endDate = event.endDate)
                _state.value = _state.value.copy(endDateError = null)
            }
            is CreatingPostEvent.ResetEndDateChange -> {
                _state.value = _state.value.copy(endDate = event.endDate)
                _state.value = _state.value.copy(endDateError = null)
            }
            is CreatingPostEvent.FilesChange -> {
                _state.value = _state.value.copy(files = event.files)
                _state.value = _state.value.copy(filesError = null)
            }
            is CreatingPostEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val content = ValidateName().execute(_state.value.content)
        //val startDate = ValidateName().execute(_state.value.startDate)
        //val endDate = ValidateUsername().execute(_state.value.endDate)
        val hasError = listOf(
            content,
            //startDate,
            //endDate
        ).any { !it.successful }
        if (hasError){
            _state.value = _state.value.copy(
                contentError = content.errorMessage,
                //startDateError = startDate.errorMessage,
                //endDateError = endDate.errorMessage
            )
            return
        }

        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun setEditPostData(
        context: Context,
        profileId: String,
        profileInfo: RequestUpdateProfileM,
        avatar: String?
    ) {
        this.profileId = profileId
        this.profileInfo = profileInfo
        this.avatar = avatar ?: ""
    }


    // endregion

    // region --- Properties ---

    val state: StateFlow<CreatingPostState>
        get() = _state

    var profileId by mutableStateOf("")
        private set

    var profileInfo by mutableStateOf<RequestUpdateProfileM?>(null)
        private set

    var avatar by mutableStateOf("")
        private set

    // endregion

    // region --- Fields ---

    private var _state = MutableStateFlow(CreatingPostState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion

}