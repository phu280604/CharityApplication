package com.developing.charityapplication.presentation.view.component.text.builder

import com.developing.charityapplication.presentation.view.component.text.TextComponent
import com.developing.charityapplication.presentation.view.component.text.TextConfig

class TextComponentBuilder {
    private var config = TextConfig()

    fun withConfig(newConfig: TextConfig) = apply { this.config = newConfig }

    fun build(): TextComponent {
        var decoratedComponent = TextComponent(config)

        return decoratedComponent
    }
}