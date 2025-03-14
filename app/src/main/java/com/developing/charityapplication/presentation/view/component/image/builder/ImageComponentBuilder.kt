package com.developing.charityapplication.presentation.view.component.image.builder

import com.developing.charityapplication.presentation.view.component.baseComponent.builder.IBaseBuilder
import com.developing.charityapplication.presentation.view.component.image.ImageComponent
import com.developing.charityapplication.presentation.view.component.image.ImageConfig
import com.developing.charityapplication.presentation.view.component.image.decorator.IImageDecorator

class ImageComponentBuilder : IBaseBuilder<IImageDecorator> {

    // region --- Overrides ---

    fun withConfig(newConfig: ImageConfig) = apply { this._config = newConfig }

    override fun build(): IImageDecorator {
        return ImageComponent(this._config)
    }

    // endregion

    // region --- Fields ---

    private var _config = ImageConfig()

    // endregion

}