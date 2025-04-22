package com.developing.charityapplication.presentation.viewmodel.screenViewModel.donation

import androidx.compose.ui.graphics.painter.Painter
import androidx.lifecycle.ViewModel
import com.developing.charityapplication.presentation.state.screenState.ShareDonationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShareDonationInfoViewModel @Inject constructor() : ViewModel() {

    // region --- Methods ---

    fun addInfo(
        targetAvatar: Painter,
        targetName: String,
        targetPostId: String,
        senderId: String,
        senderName: String
    ){
        _shareState.value = _shareState.value.copy(
            targetAvatar = targetAvatar,
            targetName = targetName,
            targetPostId = targetPostId,
            senderId = senderId,
            senderName = senderName
        )
    }

    fun addURL(
        url: String
    ){
        _sharePaymentState.value = url
    }

    // endregion

    // region --- Properties ---

    val shareState: StateFlow<ShareDonationState>
        get() = _shareState

    val sharePaymentState: StateFlow<String>
        get() = _sharePaymentState

    // endregion

    // region --- Fields ---

    private val _shareState = MutableStateFlow<ShareDonationState>(ShareDonationState())
    private val _sharePaymentState = MutableStateFlow<String>("")

    // endregion

}