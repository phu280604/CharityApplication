package com.developing.charityapplication.presentation.view.component.inputField

import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

data class InputFieldConfig (
    var value: String,
    var valueStyle: TextStyle = TextStyle(),
    var onValueChange: (String) -> Unit,
    var label: String,
    var labelStyle: TextStyle = TextStyle(),
    var color: TextFieldColors,
    var modifier: Modifier = Modifier,
    var shape: Shape = RectangleShape,
    var singleLine: Boolean = false,
    var errorSMS: String? = null,
    var supportTextStyle: TextStyle = TextStyle(),
    var isError: Boolean = false,
    var visualTransformation: VisualTransformation = VisualTransformation.None,
    var leadingIcon: @Composable (() -> Unit)? = null,
    var keyboardOptions: KeyboardOptions = KeyboardOptions.Default
)