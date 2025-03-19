package com.developing.charityapplication.presentation.view.component.inputField

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation

data class InputFieldConfig (
    var value: String = "",
    var valueStyle: TextStyle = TextStyle(),
    var onValueChange: ((String) -> Unit)? = null,
    var label: @Composable (() -> Unit)? = null,
    var placeHolder: @Composable (() -> Unit)? = null,
    var supportText: @Composable (() -> Unit)? = null,
    var isError: Boolean = false,
    var color: Any? = null,
    var modifier: Modifier = Modifier,
    var shape: Shape = RectangleShape,
    var maxLine: Int = Int.MAX_VALUE,
    var minLine: Int = 1,
    val decorateBox: (@Composable () -> Unit)? = null,
    var visualTransformation: VisualTransformation = VisualTransformation.None,
    var leadingIcon: @Composable (() -> Unit)? = null,
    var keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    var keyboardActions: KeyboardActions = KeyboardActions.Default
)