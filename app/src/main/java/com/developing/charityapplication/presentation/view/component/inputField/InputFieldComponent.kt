package com.developing.charityapplication.presentation.view.component.inputField


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.developing.charityapplication.presentation.view.component.inputField.decorator.IInputFieldComponentDecorator
import com.developing.charityapplication.presentation.view.theme.AppColorTheme

class InputFieldComponent(
    private val config: InputFieldConfig
) : IInputFieldComponentDecorator {

    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {

        OutlinedTextField(
            value = config.value,
            textStyle = config.valueStyle,
            onValueChange = config.onValueChange ?: {},
            label = config.label,
            placeholder = config.placeHolder,
            colors = config.color as? TextFieldColors ?: OutlinedTextFieldDefaults.colors(
                cursorColor = AppColorTheme.onPrimary,
                selectionColors = TextSelectionColors(
                    handleColor = AppColorTheme.onBackground,
                    backgroundColor = AppColorTheme.background
                ),
                unfocusedBorderColor = AppColorTheme.surface,
                unfocusedTextColor = AppColorTheme.onPrimary,
                focusedBorderColor = AppColorTheme.onPrimary,
                focusedTextColor = AppColorTheme.onPrimary,
                errorBorderColor = AppColorTheme.onError,
                errorLabelColor = AppColorTheme.onError,
                errorSupportingTextColor = AppColorTheme.onError,
                errorTextColor = AppColorTheme.onError
            ),
            shape = config.shape,
            isError = config.isError,
            supportingText = config.supportText,
            maxLines = config.maxLine,
            minLines = config.minLine,
            modifier = config.modifier,
            visualTransformation = config.visualTransformation,
            keyboardOptions = config.keyboardOptions,
            keyboardActions = config.keyboardActions,
            trailingIcon = config.leadingIcon,
        )
    }

    @Composable
    override fun BasicDecorate(content: @Composable (() -> Unit)) {
        TextField(
            value = config.value,
            textStyle = config.valueStyle,
            onValueChange = config.onValueChange ?: {},
            label = config.label,
            placeholder = config.placeHolder,
            colors = config.color as? TextFieldColors ?: TextFieldDefaults.colors(
                cursorColor = AppColorTheme.onPrimary,
                selectionColors = TextSelectionColors(
                    handleColor = AppColorTheme.onBackground,
                    backgroundColor = AppColorTheme.background
                ),
                unfocusedContainerColor = AppColorTheme.primary,
                focusedContainerColor = AppColorTheme.primary,
                unfocusedIndicatorColor = AppColorTheme.primary,
                unfocusedTextColor = AppColorTheme.onPrimary,
                focusedTextColor = AppColorTheme.onPrimary,
                errorLabelColor = AppColorTheme.onError,
                errorSupportingTextColor = AppColorTheme.onError,
                errorTextColor = AppColorTheme.onError
            ),
            shape = config.shape,
            isError = config.isError,
            supportingText = config.supportText,
            maxLines = config.maxLine,
            minLines = config.minLine,
            modifier = config.modifier,
            visualTransformation = config.visualTransformation,
            keyboardOptions = config.keyboardOptions,
            keyboardActions = config.keyboardActions,
            trailingIcon = config.leadingIcon,
        )
    }

    override fun getConfig(): InputFieldConfig {
        return config
    }
}