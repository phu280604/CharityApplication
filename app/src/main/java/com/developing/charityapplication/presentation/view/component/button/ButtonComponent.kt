package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.ComponentContentAlignment

interface ComponentDecorator {
    @Composable
    fun Decorate(content: @Composable () -> Unit)
    fun getConfig() : ButtonConfig
}


class ButtonComponent(
    private val config: ButtonConfig
) : ComponentDecorator {
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
            val contentAlign : ComponentContentAlignment = ButtonContentAlignment(config = config)
            contentAlign.ContentAlignment()
        }
    }

    override fun getConfig(): ButtonConfig {
        return config
    }
}