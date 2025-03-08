package com.developing.charityapplication.presentation.view.component.button.builder

import com.developing.charityapplication.presentation.view.component.button.ButtonComponent
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig

class ButtonComponentBuilder {
    private var config = ButtonConfig()

    fun withConfig(newConfig: ButtonConfig) = apply { this.config = newConfig }

    fun build(): ButtonComponent {
        var decoratedComponent = ButtonComponent(config)

        return decoratedComponent
    }
}