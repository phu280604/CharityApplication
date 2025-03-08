package com.developing.charityapplication.presentation.view.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle

// region - BASE DECORATOR -

abstract class BaseButtonDecorator(
    protected var wrapped: ComponentDecorator
) : ComponentDecorator {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        baseConfig = (wrapped as? ButtonComponent)?.getConfig()?.copy() ?: nextWrapped(content)
    }

    override fun getConfig(): ButtonConfig {
        return baseConfig ?: wrapped.getConfig()
    }

    // endregion

    // region --- Methods ---

    @Composable
    fun nextWrapped(content: @Composable (() -> Unit)) : ButtonConfig{
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

class TextDecorator(
    private val text: String,
    wrapped: ComponentDecorator,
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

class TextStyleDecorator(
    private val textStyle: TextStyle,
    wrapped: ComponentDecorator,
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

class OnClickDecorator(
    private val customOnClick: () -> Unit,
    wrapped: ComponentDecorator,
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

class IsIconDecorator(
    private val isIcon: Boolean,
    private val iconRes: Int,
    private val isRow: Boolean = true,
    wrapped: ComponentDecorator,
    private var isParent: Boolean = false
) : BaseButtonDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.isIcon = isIcon
        baseConfig?.iconRes = iconRes
        baseConfig?.isRow = isRow
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ColorDecorator(
    private val colors: ButtonColors,
    wrapped: ComponentDecorator,
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

class ShapeDecorator(
    private val shape: Shape,
    wrapped: ComponentDecorator,
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

class ContentPaddingDecorator(
    private val contentPadding: PaddingValues,
    wrapped: ComponentDecorator,
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

class EnableDecorator(
    private val enable: Boolean,
    wrapped: ComponentDecorator,
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

class ModifierDecorator(
    private val customModifier: Modifier,
    wrapped: ComponentDecorator,
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