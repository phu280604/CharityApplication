package com.developing.charityapplication.presentation.view.component.button.decorator

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.baseComponent.decorator.IBaseDecorator
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig

interface IButtonComponentDecotator : IBaseDecorator<ButtonConfig> {
    @Composable
    fun VerticalAlignmentDecorate(content: @Composable (() -> Unit))
}