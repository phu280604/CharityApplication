package com.developing.charityapplication.presentation.view.component.inputField


import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.inputField.decorator.IInputFieldComponentDecorator

class InputFieldComponent(
    private val config: InputFieldConfig
) : IInputFieldComponentDecorator {

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {

        OutlinedTextField(
            value = config.value,
            textStyle = config.valueStyle,
            onValueChange = config.onValueChange ?: {},
            label = config.label,
            placeholder = config.placeHolder,
            colors = config.color ?: OutlinedTextFieldDefaults.colors(),
            shape = config.shape,
            isError = config.isError,
            supportingText = config.supportText,
            maxLines = config.maxLine,
            minLines = config.minLine,
            modifier = config.modifier,
            visualTransformation = config.visualTransformation,
            keyboardOptions = config.keyboardOptions,
            trailingIcon = config.leadingIcon,
        )
    }

    override fun getConfig(): InputFieldConfig {
        return config
    }
}