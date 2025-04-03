package com.developing.charityapplication.presentation.viewmodel.componentViewModel.postState

import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue

class PostState {
    var _expended by mutableStateOf(false)
        private set

    fun toggleExpendState(){
        _expended = !_expended
    }
}