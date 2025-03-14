package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.developing.charityapplication.presentation.view.theme.AppTypography

data class ButtonConfig(
    var text: String = "",
    var textStyle: TextStyle = AppTypography.titleMedium,
    var onClick: () -> Unit = {},
    var imageRes: Int? = null,
    var iconRes: Int? = null,
    var colors: ButtonColors = ButtonColors(
        containerColor = Color.Unspecified,
        contentColor = Color.Unspecified,
        disabledContentColor = Color.Unspecified,
        disabledContainerColor = Color.Unspecified
    ),
    var contentPadding: PaddingValues? = null,
    var modifier: Modifier = Modifier,
    var shape: Shape? = null,
    var enable: Boolean = true,
    var isHorizontal: Boolean = true
)
