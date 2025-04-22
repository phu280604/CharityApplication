package com.developing.charityapplication.presentation.viewmodel.serviceViewModel.donationViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developing.charityapplication.data.authentication.TokenProvider
import com.developing.charityapplication.domain.model.donationModel.RequestDonationM
import com.developing.charityapplication.domain.model.donationModel.ResponseDonationM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.*
import com.developing.charityapplication.domain.model.utilitiesModel.ResponseM
import com.developing.charityapplication.domain.model.utilitiesModel.ResultM
import com.developing.charityapplication.domain.repoInter.donationRepoInter.IDonationRepo
import com.developing.charityapplication.domain.repoInter.identityRepoInter.IAuthRepo
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.RegisterFormViewModel.ValidationEvent
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.loading.LoadingViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(
    private val repo: IDonationRepo,
): ViewModel() {

    // region --- Methods ---

    fun createDonation(requestDonation: RequestDonationM) {
        viewModelScope.launch {
            LoadingViewModel.enableLoading(true)
            try {
                val result = repo.createDonation(requestDonation)
                _donationResponse.value = result
            } catch (e: Exception) {
                Log.e("API_ERROR", "Lỗi khi gọi API", e)
            } finally {
                LoadingViewModel.enableLoading()
            }
        }
    }

    fun resetData(){
        _donationResponse.value = null
    }

    // endregion

    // region --- Properties ---

    val donationResponse: StateFlow<ResponseM<ResponseDonationM>?>
        get() = _donationResponse

    // endregion

    // region --- Fields ---

    private val _donationResponse = MutableStateFlow<ResponseM<ResponseDonationM>?>(null)

    // endregion

}