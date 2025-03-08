package com.developing.charityapplication.presentation.view.component.factory

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig

interface ComponentFactory {
    @Composable
    fun CreateOutlinedInputField(config: InputFieldConfig)

    @Composable
    fun CreateFilledButton(config: ButtonConfig)

    @Composable
    fun CreateOutlinedButton(config: ButtonConfig)

    @Composable
    fun CreateTextButton(config: ButtonConfig)
}