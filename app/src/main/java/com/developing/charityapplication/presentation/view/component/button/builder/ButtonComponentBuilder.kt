package com.developing.charityapplication.presentation.view.component.button.builder

import com.developing.charityapplication.presentation.view.component.baseComponent.builder.IBaseBuilder
import com.developing.charityapplication.presentation.view.component.button.ButtonComponent
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.decorator.IButtonComponentDecotator

class ButtonComponentBuilder : IBaseBuilder<IButtonComponentDecotator> {
    private var config = ButtonConfig()

    fun withConfig(newConfig: ButtonConfig) = apply { this.config = newConfig }

    override fun build(): IButtonComponentDecotator {
        return ButtonComponent(config)
    }
}