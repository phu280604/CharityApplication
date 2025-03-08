package com.developing.charityapplication.presentation.view.component.button.decorator

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig

interface IButtonComponentDecotator {
    @Composable
    fun Decorate(content: @Composable () -> Unit)
    fun getConfig() : ButtonConfig
}