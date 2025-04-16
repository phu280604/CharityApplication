package com.developing.charityapplication.presentation.view.component.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.developing.charityapplication.presentation.view.component.image.decorator.IImageDecorator
import com.developing.charityapplication.R

class ImageComponent(
    private val config: ImageConfig
) : IImageDecorator{

    // region --- Overrides ---

    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Image(
            painter = config.painter ?: painterResource(id = R.drawable.avt_young_girl),
            contentDescription = config.contentDescription,
            modifier = config.modifier,
            alignment = config.alignment,
            contentScale = config.contentScale,
            alpha = config.alpha,
            colorFilter = config.colorFilter
        )
    }

    override fun getConfig(): ImageConfig = config

    // endregion

}