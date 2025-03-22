package com.developing.charityapplication.presentation.view.component.image

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale

data class ImageConfig(
    var modifier: Modifier = Modifier,
    var painter: Int = 0,
    var contentDescription: String? = null,
    var alignment: Alignment = Alignment.Center,
    var contentScale: ContentScale = ContentScale.Fit,
    var alpha: Float = 1.0f,
    var colorFilter: ColorFilter? = null
)