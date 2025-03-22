package com.developing.charityapplication.presentation.view.component.text

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

data class TextConfig (
    var text: String = "",
    var textStyle: TextStyle = TextStyle(),
    var color: Color = Color.Unspecified,
    var textAlign: TextAlign? = null,
    var maxLine: Int = Int.MAX_VALUE,
    var minLine: Int = 1,
    var modifier: Modifier = Modifier,
)
