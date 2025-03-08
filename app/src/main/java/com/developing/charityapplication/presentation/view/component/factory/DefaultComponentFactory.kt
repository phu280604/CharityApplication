package com.developing.charityapplication.presentation.view.component.factory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.ComponentContentAlignment
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.ButtonContentAlignment
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.theme.Green

class DefaultComponentFactory : ComponentFactory {

    // region --- Overrides ---

    @Composable
    override fun CreateOutlinedInputField(config: InputFieldConfig) {
        OutlinedTextField(
            value = config.value,
            onValueChange = config.onValueChange,
            label = {
                Text(
                    text = config.label,
                    style = config.labelStyle
                )
            },
            isError = config.isError,
            modifier = config.modifier,
            shape = config.shape,
            colors = config.color,
            supportingText = {
                if (!config.errorSMS.isNullOrEmpty()){
                    Text(
                        text = config.errorSMS.toString(),
                        style = config.supportTextStyle
                    )
                }
            },
            visualTransformation = config.visualTransformation,
            keyboardOptions = config.keyboardOptions,
            trailingIcon = config.leadingIcon,
            singleLine = config.singleLine,
            textStyle = config.valueStyle
        )
    }

    // region -- Button Component --
    @Composable
    override fun CreateFilledButton(config: ButtonConfig) {
        /*Button(
            onClick = config.onClick,
            modifier = config.modifier,
            shape = config.shape,
            contentPadding = config.contentPadding,
            colors = config.colors,
            enabled = config.enable
        ) {
            contentAlignment = ButtonContentAlignment(config)
            contentAlignment?.ContentAlignment()
        }*/
    }

    @Composable
    override fun CreateOutlinedButton(config: ButtonConfig) {
        /*OutlinedButton(
            onClick = config.onClick,
            modifier = config.modifier,
            shape = config.shape,
            contentPadding = config.contentPadding,
            border = BorderStroke(1.dp, Green),
            colors = config.colors,
            enabled = config.enable
        ) {
            contentAlignment = ButtonContentAlignment(config)
            contentAlignment?.ContentAlignment()
        }*/
    }

    @Composable
    override fun CreateTextButton(config: ButtonConfig) {
        /*TextButton(
            onClick = config.onClick,
            modifier = config.modifier,
            shape = config.shape,
            contentPadding = config.contentPadding,
            colors = config.colors,
            enabled = config.enable
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                contentAlignment = ButtonContentAlignment(config)
                contentAlignment?.ContentAlignment()
            }
        }*/
    }

    // endregion

    // region --- Fields ---

    private var contentAlignment : ComponentContentAlignment? = null

    // endregion
}