package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.developing.charityapplication.presentation.view.theme.AppTypography

data class ButtonConfig(
    var text: String = "",
    var textStyle: TextStyle = AppTypography.titleMedium,
    var onClick: () -> Unit = {},
    var content: @Composable (() -> Unit)? = null,
    var colors: Any? = null,
    var contentPadding: PaddingValues? = null,
    var modifier: Modifier = Modifier,
    var shape: Shape? = null,
    var enable: Boolean = true,
    var spacer: Int = 0
)
