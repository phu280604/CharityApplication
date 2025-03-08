package com.developing.charityapplication.presentation.view.component.inputField.decorator

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig

interface IInputFieldComponentDecorator {
    @Composable
    fun Decorate(content: @Composable () -> Unit)
    fun getConfig() : InputFieldConfig
}