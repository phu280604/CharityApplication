package com.developing.charityapplication.presentation.view.component.text.decorator

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.developing.charityapplication.presentation.view.component.text.TextComponent
import com.developing.charityapplication.presentation.view.component.text.TextConfig

// region - BASE DECORATOR -

abstract class BaseTextDecorator(
    protected var wrapped: ITextComponentDecorator
) : ITextComponentDecorator {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        when (wrapped) {
            is TextComponent -> {
                baseConfig = wrapped.getConfig().copy()
                return
            }
            else -> baseConfig = nextWrapped(content)
        }
    }

    override fun getConfig(): TextConfig {
        return baseConfig ?: wrapped.getConfig()
    }

    // endregion

    // region --- Methods ---

    @Composable
    fun nextWrapped(content: @Composable (() -> Unit)) : TextConfig {
        wrapped.Decorate(content)
        return wrapped.getConfig().copy()
    }

    @Composable
    open fun ParentWrapped(content: @Composable (() -> Unit)){
        TextComponent(baseConfig!!).Decorate(content)
    }

    // endregion

    // region --- Fields ---

    protected var baseConfig: TextConfig? = null

    // endregion

}

// endregion

// region - TEXT -

class TextDecorator(
    private val text: String,
    wrapped: ITextComponentDecorator,
    private var isParent: Boolean = false
) : BaseTextDecorator(wrapped) {

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
    wrapped: ITextComponentDecorator,
    private var isParent: Boolean = false
) : BaseTextDecorator(wrapped) {

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

class TextAlignDecorator(
    private val textAlign: TextAlign,
    wrapped: ITextComponentDecorator,
    private var isParent: Boolean = false
) : BaseTextDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.textAlign = textAlign
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

class ColorDecorator(
    private val color: Color,
    wrapped: ITextComponentDecorator,
    private var isParent: Boolean = false
) : BaseTextDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.color = color
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class LineDecorator(
    private val maxLine: Int = Int.MAX_VALUE,
    private val minLine: Int = 1,
    wrapped: ITextComponentDecorator,
    private var isParent: Boolean = false
) : BaseTextDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.maxLine = maxLine
        baseConfig?.minLine = minLine
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class ModifierDecorator(
    private val modifier: Modifier,
    wrapped: ITextComponentDecorator,
    private var isParent: Boolean = false
) : BaseTextDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.modifier = modifier
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

// endregion