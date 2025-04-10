package com.developing.charityapplication.presentation.viewmodel.componentViewModel.postState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue

class DropDownMenuState {

    val expended: Boolean
        get() = _expended

    private var _expended by mutableStateOf(false)

    fun toggleExpendState(){
        _expended = !_expended
    }
}