package com.developing.charityapplication.presentation.viewmodel.screenViewModel.profile

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
import com.developing.charityapplication.presentation.event.screenEvent.EditProfileEvent
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
class EditProfileViewModel @Inject constructor(): ViewModel() {

    // region --- Sealed Class ---

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

    // endregion

    // region --- Methods ---

    fun onEvent(event: EditProfileEvent){
        when(event){
            is EditProfileEvent.FirstNameChange -> {
                _state.value = _state.value.copy(firstName = event.firstName)
                _state.value = _state.value.copy(firstNameError = null)
            }
            is EditProfileEvent.LastNameChange -> {
                _state.value = _state.value.copy(lastName = event.lastName)
                _state.value = _state.value.copy(lastNameError = null)
            }
            is EditProfileEvent.UsernameChange -> {
                _state.value = _state.value.copy(username = event.username)
                _state.value = _state.value.copy(usernameError = null)
            }
            is EditProfileEvent.LocationChange -> {
                _state.value = _state.value.copy(location = event.location)
                _state.value = _state.value.copy(locationError = null)
            }
            is EditProfileEvent.AvatarChange -> {
                _state.value = _state.value.copy(avatar = event.avatar)
                _state.value = _state.value.copy(avatarError = null)
            }
            is EditProfileEvent.Submit -> {
                submitData()
            }
        }
    }

    fun submitData(){
        val firstName = ValidateName().execute(_state.value.firstName)
        val lastName = ValidateName().execute(_state.value.lastName)
        val username = ValidateUsername().execute(_state.value.username)
        val hasError = listOf(
            firstName,
            lastName,
            username
        ).any { !it.successful }
        if (hasError){
            _state.value = _state.value.copy(
                firstNameError = firstName.errorMessage,
                lastNameError = lastName.errorMessage,
                usernameError = username.errorMessage
            )
            return
        }

        viewModelScope.launch{
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    fun setEditProfileData(
        context: Context,
        profileId: String,
        profileInfo: RequestUpdateProfileM,
        avatar: String?
    ) {
        this.profileId = profileId
        this.profileInfo = profileInfo
        this.avatar = avatar ?: ""

        viewModelScope.launch{
            if (avatar?.isEmpty() == true)
            {
                val downloadImage = DownloadImage.prepareImageParts(context, avatar, "avatar")
                _state.value = _state.value.copy(
                    avatar = downloadImage
                )
            }
        }
    }


    // endregion

    // region --- Properties ---

    val state: StateFlow<EditProfileState>
        get() = _state

    var profileId by mutableStateOf("")
        private set

    var profileInfo by mutableStateOf<RequestUpdateProfileM?>(null)
        private set

    var avatar by mutableStateOf("")
        private set

    // endregion

    // region --- Fields ---

    private var _state = MutableStateFlow(EditProfileState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    // endregion

}