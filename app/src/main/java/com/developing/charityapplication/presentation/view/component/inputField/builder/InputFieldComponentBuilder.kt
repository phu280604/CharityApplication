package com.developing.charityapplication.presentation.view.component.inputField.builder

import com.developing.charityapplication.presentation.view.component.baseComponent.builder.IBaseBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldComponent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.decorator.IInputFieldComponentDecorator
import com.developing.charityapplication.presentation.view.component.text.TextComponent
import com.developing.charityapplication.presentation.view.component.text.TextConfig

class InputFieldComponentBuilder : IBaseBuilder<IInputFieldComponentDecorator> {
    private var config = InputFieldConfig()

    fun withConfig(newConfig: InputFieldConfig) = apply { this.config = newConfig }

    override fun build(): IInputFieldComponentDecorator = InputFieldComponent(config)
}