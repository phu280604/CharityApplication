package com.developing.charityapplication.presentation.view.component.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.text.decorator.ITextComponentDecorator

class TextComponent(
    private val config: TextConfig
) : ITextComponentDecorator {

    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Text(
            text = config.text,
            style = config.textStyle,
            color = config.color,
            textAlign = config.textAlign,
            maxLines = config.maxLine,
            minLines = config.minLine,
            modifier = config.modifier
        )
    }

    override fun getConfig(): TextConfig = config
}