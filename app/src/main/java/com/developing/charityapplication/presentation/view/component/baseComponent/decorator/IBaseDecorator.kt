package com.developing.charityapplication.presentation.view.component.baseComponent.decorator

import androidx.compose.runtime.Composable

interface IBaseDecorator<T> {

    // region --- Methods ---

    fun getConfig() : T

    @Composable
    fun BaseDecorate(content: @Composable () -> Unit)

    // endregion

}