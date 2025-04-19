package com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.developing.charityapplication.R

object HeaderViewModel{

    // region --- Methods ---

    fun changeSelectedIndex(index: Int = R.string.nav_home)
    {
        _selectedIndexState.value = index
    }

    // endregion

    // region --- Properties ---


    val selectedIndexState: StateFlow<Int>
        get() = _selectedIndexState

    // endregion

    // region --- Fields ---

    private val _selectedIndexState = MutableStateFlow(0)

    // endregion

}