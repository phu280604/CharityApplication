package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.button.decorator.IButtonComponentDecotator


class ButtonComponent(
    private val config: ButtonConfig
) : IButtonComponentDecotator {
    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        Button(
            onClick = config.onClick,
            modifier = config.modifier,
            shape = config.shape ?: RoundedCornerShape(8.dp),
            contentPadding = config.contentPadding ?: PaddingValues(0.dp),
            colors = config.colors,
            enabled = config.enable
        ) {
            val contentAlign : IButtonContentAlignment = ButtonContentAlignment(config = config)
            if (config.isHorizontal)
                contentAlign.HorizontalAlignment()
            else
                contentAlign.VerticalAlignment()
        }
    }

    override fun getConfig(): ButtonConfig {
        return config
    }
}


