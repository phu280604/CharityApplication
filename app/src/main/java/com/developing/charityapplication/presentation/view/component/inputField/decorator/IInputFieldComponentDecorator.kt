package com.developing.charityapplication.presentation.view.component.inputField.decorator

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.baseComponent.decorator.IBaseDecorator
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig

interface IInputFieldComponentDecorator : IBaseDecorator<InputFieldConfig> {
    @Composable
    fun BasicDecorate(content: @Composable (() -> Unit))
}