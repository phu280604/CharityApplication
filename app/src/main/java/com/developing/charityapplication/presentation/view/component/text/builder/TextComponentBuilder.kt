package com.developing.charityapplication.presentation.view.component.text.builder

import com.developing.charityapplication.presentation.view.component.baseComponent.builder.IBaseBuilder
import com.developing.charityapplication.presentation.view.component.text.TextComponent
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.decorator.ITextComponentDecorator

class TextComponentBuilder : IBaseBuilder<ITextComponentDecorator> {
    private var config = TextConfig()

    fun withConfig(newConfig: TextConfig) = apply { this.config = newConfig }

    override fun build(): ITextComponentDecorator = TextComponent(config)
}