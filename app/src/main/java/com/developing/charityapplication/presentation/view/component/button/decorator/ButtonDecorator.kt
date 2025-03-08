package com.developing.charityapplication.presentation.view.component.button.decorator

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.developing.charityapplication.presentation.view.component.button.ButtonComponent
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.text.decorator.BaseTextDecorator
import com.developing.charityapplication.presentation.view.component.text.decorator.ITextComponentDecorator

// region - BASE DECORATOR -

abstract class BaseButtonDecorator(
    protected var wrapped: IButtonComponentDecotator
) : IButtonComponentDecotator {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        when (wrapped) {
            is ButtonComponent -> {
                baseConfig = wrapped.getConfig().copy()
                return
            }

            else -> baseConfig = nextWrapped(content)
        }
    }

    override fun getConfig(): ButtonConfig {
        return baseConfig ?: wrapped.getConfig()
    }

    // endregion

    // region --- Methods ---

    @Composable
    fun nextWrapped(content: @Composable (() -> Unit)) : ButtonConfig {
        wrapped.Decorate(content)
        return wrapped.getConfig().copy()
    }

    @Composable
    open fun ParentWrapped(content: @Composable (() -> Unit)){
        ButtonComponent(baseConfig!!).Decorate(content)
    }

    // endregion

    // region --- Fields ---

    protected var baseConfig: ButtonConfig? = null

    // endregion

}

// endregion

// region - TEXT -

class ButtonTextDecorator(
    private val text: String,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.text = text
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ButtonTextStyleDecorator(
    private val textStyle: TextStyle,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.textStyle = textStyle
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

// endregion

// region - ACTION -

class ButtonOnClickDecorator(
    private val customOnClick: () -> Unit,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.onClick = customOnClick
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

// endregion

// region - OPTIONAL -

class ButtonIsIconDecorator(
    private val isIcon: Boolean,
    private val iconRes: Int,
    private val isHorizontal: Boolean = true,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.isIcon = isIcon
        baseConfig?.iconRes = iconRes
        baseConfig?.isHorizontal = isHorizontal
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ButtonColorDecorator(
    private val colors: ButtonColors,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable () -> Unit) {
        super.Decorate(content)

        baseConfig?.colors = colors
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

// endregion

// region - CUSTOM DESIGN -

class ButtonShapeDecorator(
    private val shape: Shape,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.shape = shape
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ButtonContentPaddingDecorator(
    private val contentPadding: PaddingValues,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.contentPadding = contentPadding
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ButtonEnableDecorator(
    private val enable: Boolean,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.enable = enable
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ButtonModifierDecorator(
    private val customModifier: Modifier,
    wrapped: IButtonComponentDecotator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.modifier = customModifier
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

// endregion