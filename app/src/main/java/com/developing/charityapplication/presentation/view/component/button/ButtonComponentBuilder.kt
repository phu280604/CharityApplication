package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class ButtonComponentBuilder {
    private var config = ButtonConfig()

    fun withConfig(newConfig: ButtonConfig) = apply { this.config = newConfig }

    fun withText(text: String) = apply { config.text = text }
    fun withOnClick(onClick: () -> Unit) = apply { config.onClick = onClick }
    fun withIcon(iconRes: Int) = apply { config.isIcon = true; config.iconRes = iconRes }
    fun withEnabled(enabled: Boolean) = apply { config.enable = enabled }

    fun withColors(colors: ButtonColors) = apply {
        config.colors = colors
    }

    fun withCustomModifier(modifier: Modifier) = apply {
        config.modifier = modifier
    }

    @Composable
    fun build(): ButtonComponent {
        if (config.contentPadding == null) {
            config.contentPadding = PaddingValues(0.dp)
        }
        if (config.shape == null) {
            config.shape = RoundedCornerShape(8.dp)
        }

        var decoratedComponent = ButtonComponent(config)

        return decoratedComponent
    }
}