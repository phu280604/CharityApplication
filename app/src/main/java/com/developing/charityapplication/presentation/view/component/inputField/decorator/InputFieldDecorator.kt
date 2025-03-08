package com.developing.charityapplication.presentation.view.component.inputField.decorator

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.VisualTransformation
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldComponent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig

// region - BASE DECORATOR -

abstract class BaseInputFieldDecorator(
    protected var wrapped : IInputFieldComponentDecorator
) : IInputFieldComponentDecorator {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        when (wrapped) {
            is InputFieldComponent -> {
                baseConfig = wrapped.getConfig().copy()
                return
            }
            else -> baseConfig = nextWrapped(content)
        }
    }

    override fun getConfig(): InputFieldConfig {
        return baseConfig ?: wrapped.getConfig()
    }

    // endregion

    // region --- Methods ---

    @Composable
    fun nextWrapped(content: @Composable (() -> Unit)) : InputFieldConfig {
        wrapped.Decorate(content)
        return wrapped.getConfig().copy()
    }

    @Composable
    open fun ParentWrapped(content: @Composable (() -> Unit)){
        InputFieldComponent(baseConfig!!).Decorate(content)
    }

    // endregion

    // region --- Fields ---

    protected var baseConfig: InputFieldConfig? = null

    // endregion

}

// endregion

// region - TEXT -

class InputFieldValueDecorator(
    private val value: String,
    private val onValueChange: (String) -> Unit,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.value = value
        baseConfig?.onValueChange = onValueChange
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldLabelDecorator(
    private val label: @Composable (() -> Unit),
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.label = label
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldPlaceHolderDecorator(
    private val placeHolder: @Composable (() -> Unit),
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.placeHolder = placeHolder
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldSupportTextDecorator(
    private val supportingText: @Composable (() -> Unit),
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.supportText = supportingText
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldLineDecorator(
    private val maxLine: Int = Int.MAX_VALUE,
    private val minLine: Int = 1,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

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

// endregion

// region - OPTIONAL -

class InputFieldModifierDecorator(
    private val modifier: Modifier,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

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

class InputFieldColorDecorator(
    private val color: TextFieldColors,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

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

class InputFieldIsErrorDecorator(
    private val isError: Boolean,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.isError = isError
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldShapeDecorator(
    private val shape: Shape,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

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

class InputFieldVisualTransformationDecorator(
    private val visualTransformation: VisualTransformation,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.visualTransformation = visualTransformation
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldleadingIconDecorator(
    private val leadingIcon: @Composable (() -> Unit),
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.leadingIcon = leadingIcon
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

class InputFieldkeyboardOptionsDecorator(
    private val keyboardOptions: KeyboardOptions,
    wrapped: IInputFieldComponentDecorator,
    private var isParent: Boolean = false
) : BaseInputFieldDecorator(wrapped) {

    // region --- Overrides ---

    @Composable
    override fun Decorate(content: @Composable (() -> Unit)) {
        super.Decorate(content)

        baseConfig?.keyboardOptions = keyboardOptions
        if (isParent) ParentWrapped(content)
    }

    @Composable
    override fun ParentWrapped(content: @Composable (() -> Unit)) {
        super.ParentWrapped(content)
    }

    // endregion

}

// endregion