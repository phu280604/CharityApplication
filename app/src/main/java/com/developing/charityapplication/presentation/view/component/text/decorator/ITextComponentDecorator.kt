package com.developing.charityapplication.presentation.view.component.text.decorator

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.text.TextConfig

interface ITextComponentDecorator {
    @Composable
    fun Decorate(content: @Composable () -> Unit)
    fun getConfig() : TextConfig
}