package com.developing.charityapplication.presentation.viewmodel.screenViewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(): ViewModel() {

    // region --- Methods ---

    fun changeSelectedIndex(index: Int)
    {
        _selectedIndexState.intValue = index
    }

    // endregion

    // region --- Properties ---

    val selectedIndexState: State<Int>
        get() = _selectedIndexState

    // endregion

    // region --- Fields ---

    private var _selectedIndexState = mutableIntStateOf(0)

    // endregion

}