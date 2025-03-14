package com.developing.charityapplication.presentation.view.component.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.developing.charityapplication.presentation.view.component.image.decorator.IImageDecorator

class ImageComponent(
    private val config: ImageConfig
) : IImageDecorator{

    // region --- Overrides ---

    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Image(
            painter = painterResource(id = config.painter),
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