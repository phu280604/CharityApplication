package com.developing.charityapplication.presentation.view.component.button

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.button.decorator.IButtonComponentDecotator


class ButtonComponent(
    private val config: ButtonConfig
) : IButtonComponentDecotator {
    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Button(
            onClick = config.onClick,
            modifier = config.modifier,
            shape = config.shape ?: RoundedCornerShape(8.dp),
            contentPadding = config.contentPadding ?: PaddingValues(0.dp),
            colors = config.colors as? ButtonColors ?: ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.surface
            ),
            enabled = config.enable
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(config.spacer.dp, Alignment.CenterHorizontally)
            ) {
                if (config.content != null){
                    config.content?.invoke()
                }

                if (!config.text.isEmpty())
                    Text(
                        text = config.text,
                        style = config.textStyle
                    )
            }
        }
    }

    @Composable
    override fun VerticalAlignmentDecorate(content: @Composable (() -> Unit)) {
        Button(
            onClick = config.onClick,
            modifier = config.modifier,
            shape = config.shape ?: RoundedCornerShape(8.dp),
            contentPadding = config.contentPadding ?: PaddingValues(0.dp),
            colors = config.colors as? ButtonColors ?: ButtonDefaults.buttonColors(),
            enabled = config.enable
        ) {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(config.spacer.dp, Alignment.CenterVertically)
            ) {
                if (config.content != null){
                    config.content?.invoke()
                }

                if (!config.text.isEmpty())
                    Text(
                        text = config.text,
                        style = config.textStyle
                    )
            }
        }
    }

    override fun getConfig(): ButtonConfig = config
}


