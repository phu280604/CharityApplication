package com.developing.charityapplication.presentation.view.component.inputField.builder

import com.developing.charityapplication.presentation.view.component.inputField.InputFieldComponent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.text.TextComponent
import com.developing.charityapplication.presentation.view.component.text.TextConfig

class InputFieldComponentBuilder {
    private var config = InputFieldConfig()

    fun withConfig(newConfig: InputFieldConfig) = apply { this.config = newConfig }

    fun build(): InputFieldComponent {
        var decoratedComponent = InputFieldComponent(config)

        return decoratedComponent
    }
}