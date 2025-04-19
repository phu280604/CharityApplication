package com.developing.charityapplication.presentation.view.component.post.builder

import com.developing.charityapplication.presentation.view.component.baseComponent.builder.IBaseBuilder
import com.developing.charityapplication.presentation.view.component.post.PostComponent
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.component.post.decorator.IPostComponentDecorator

class PostComponentBuilder : IBaseBuilder<IPostComponentDecorator> {

    // region --- Overrides ---

    override fun build(): IPostComponentDecorator =
        PostComponent(_config)

    // endregion

    // region --- Methods ---

    fun withConfig(newConfig: PostConfig) = apply { this._config = newConfig }

    // endregion

    // region --- Fields ---

    private var _config = PostConfig(
        maximizeImage = {}
    )

    // endregion

}