package com.developing.charityapplication.presentation.viewmodel.screenViewModel.loading

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

object LoadingViewModel: ViewModel() {

    // region --- Methods ---
    fun enableLoading(isEnable: Boolean = false){
        _isLoading.value = isEnable
    }
    // endregion

    // region --- Properties ---
    val isLoading: StateFlow<Boolean>
        get() = _isLoading
    // endregion

    // region --- Fields ---
    private val _isLoading = MutableStateFlow<Boolean>(false)
    // endregion

}