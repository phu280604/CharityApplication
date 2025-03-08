package com.developing.charityapplication.presentation.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.button.ButtonComponent
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.decorator.ButtonColorDecorator
import com.developing.charityapplication.presentation.view.component.button.decorator.ButtonIsIconDecorator
import com.developing.charityapplication.presentation.view.component.button.decorator.ButtonModifierDecorator
import com.developing.charityapplication.presentation.view.component.button.decorator.ButtonOnClickDecorator
import com.developing.charityapplication.presentation.view.component.button.decorator.ButtonTextDecorator
import com.developing.charityapplication.presentation.view.component.button.decorator.IButtonComponentDecotator
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldComponent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.inputField.decorator.IInputFieldComponentDecorator
import com.developing.charityapplication.presentation.view.component.inputField.decorator.InputFieldColorDecorator
import com.developing.charityapplication.presentation.view.component.inputField.decorator.InputFieldLabelDecorator
import com.developing.charityapplication.presentation.view.component.inputField.decorator.InputFieldValueDecorator
import com.developing.charityapplication.presentation.view.component.inputField.decorator.InputFieldleadingIconDecorator
import com.developing.charityapplication.presentation.view.component.text.TextComponent
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.decorator.ColorDecorator
import com.developing.charityapplication.presentation.view.component.text.decorator.ITextComponentDecorator
import com.developing.charityapplication.presentation.view.component.text.decorator.ModifierDecorator
import com.developing.charityapplication.presentation.view.component.text.decorator.TextAlignDecorator
import com.developing.charityapplication.presentation.view.component.text.decorator.TextDecorator
import com.developing.charityapplication.presentation.view.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            HeartBellTheme {
                Scaffold { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(innerPadding)
                    ) {


                        LoginCard(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                        )
                    }

                }
            }
        }
    }

    // region -- Login Card Fragment --
    // region -- Create Login Default Component --
    fun createDefaultButton(color: ColorScheme) : ButtonComponent{
        return ButtonComponentBuilder()
            .withConfig(
                ButtonConfig(
                    colors = ButtonColors(
                        containerColor = color.secondary,
                        contentColor = color.onSecondaryContainer,
                        disabledContainerColor = color.surface,
                        disabledContentColor = color.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                )
            )
            .build()
    }

    fun createDefaultInputField(textStyle: Typography) : InputFieldComponent{
        return InputFieldComponentBuilder()
            .withConfig(
                InputFieldConfig(
                    valueStyle = textStyle.titleMedium,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            )
            .build()
    }

    fun createDefaultText(color: ColorScheme, textStyle: Typography) : TextComponent{
        return TextComponentBuilder()
            .withConfig(
                TextConfig(
                    color = color.onPrimary,
                    textStyle = textStyle.bodyMedium
                )
            )
            .build()
    }
    // endregion

    @Composable
    fun LoginCard(
        modifier: Modifier = Modifier
    ) {
        // region - Remember Theme -
        val color by rememberUpdatedState(MaterialTheme.colorScheme)
        val typography by rememberUpdatedState(AppTypography)
        //endregion

        // region - Remember Buttons -
        val defaultButton = remember {
            createDefaultButton(color)
        }
        // endregion

        // region - Remember Texts -
        val defaultText = remember {
            createDefaultText(color, typography)
        }
        // endregion

        // region - Remember TextFields -
        val defaultInputField = remember {
            createDefaultInputField(typography)
        }
        // endregion

        Card(
            modifier = modifier,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = 16.dp,
                        vertical = 24.dp
                    )
            ) {
                HeaderSection()

                BodySection(
                    defaultInputField = defaultInputField,
                    defaultButton = defaultButton,
                    defaultText = defaultText,
                    color = color,
                    typography = typography
                )

                FooterSection(
                    defaultButton = defaultButton,
                    defaultText = defaultText,
                    color = color
                )
            }
        }
    }
    // endregion

    // region -- Header Section --
    @Composable
    fun HeaderSection(){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "HeartBell Logo",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
    // endregion

    // region -- Body Section --
    @Composable
    fun BodySection(
        defaultInputField : IInputFieldComponentDecorator,
        defaultButton : IButtonComponentDecotator,
        defaultText: ITextComponentDecorator,
        color: ColorScheme,
        typography: Typography
    ){
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region - Input Field Section -
            // Username/Email Input Field
            val usernameInputField = InputFieldValueDecorator(
                isParent = true,
                value = username,
                onValueChange = { username = it },
                wrapped = InputFieldColorDecorator(
                    color = OutlinedTextFieldDefaults.colors(
                        cursorColor = color.onPrimary,
                        selectionColors = TextSelectionColors(
                            handleColor = color.onBackground,
                            backgroundColor = color.background
                        ),
                        unfocusedBorderColor = color.surface,
                        unfocusedTextColor = color.onPrimary,
                        focusedBorderColor = color.secondary,
                        focusedTextColor = color.onPrimary,
                        focusedLabelColor = color.onPrimary.copy(alpha = 0.8f),
                        unfocusedLabelColor = color.onPrimary.copy(alpha = 0.8f),
                        errorTextColor = color.onError
                    ),
                    wrapped = InputFieldLabelDecorator(
                        label = {
                            Text(
                                text = stringResource(id = R.string.username_email),
                                style = typography.titleMedium
                            )
                        },
                        wrapped = InputFieldleadingIconDecorator(
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_user),
                                    contentDescription = "User Icon",
                                    tint = color.onPrimary
                                )
                            },
                            wrapped = defaultInputField
                        )
                    )
                )
            )
            usernameInputField.Decorate {  }

            // Password Input Field
            val passwordInputField = InputFieldComponentBuilder()
                .withConfig(
                    usernameInputField.getConfig().copy(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = typography.titleMedium
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 4.dp,
                                bottom = 24.dp
                            ),
                        visualTransformation =
                        if (passwordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        leadingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    painter = painterResource(
                                        id = if (passwordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                                    ),
                                    contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                    tint = color.onPrimary
                                )
                            }
                        }
                    )
                )
                .build()
            passwordInputField.Decorate {  }
            // endregion

            // region - Button Section -
            // Login
            val loginButton = ButtonTextDecorator(
                isParent = true,
                text = stringResource(id = R.string.login),
                wrapped = ButtonOnClickDecorator(
                    customOnClick = { /*TODO: Implement login logic*/ },
                    wrapped = ButtonModifierDecorator(
                        customModifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        wrapped = defaultButton
                    )
                )
            )
            loginButton.Decorate {  }

            // Forgot Password
            val forgotPasswordButton = TextDecorator(
                isParent = true,
                text = stringResource(id = R.string.forgot_password),
                wrapped = ColorDecorator(
                    color = color.secondary,
                    wrapped = ModifierDecorator(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable(
                                onClick = { /*TODO: Implement forgot password logic*/ },
                                role = Role.Button
                            ),
                        wrapped = TextAlignDecorator(
                            textAlign = TextAlign.Center,
                            wrapped = defaultText
                        )
                    )
                )
            )
            forgotPasswordButton.Decorate {  }
            // endregion
        }
    }
    // endregion

    // region -- Footer Section --
    @Composable
    fun TextDivider(
        text: String,
        color: ColorScheme,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = color.surface,
                thickness = 1.dp
            )

            Text(
                text = text,
                style = AppTypography.bodyMedium.copy(
                    color = color.surface
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Divider(
                modifier = Modifier.weight(1f),
                color = color.surface,
                thickness = 1.dp
            )
        }
    }

    @Composable
    fun FooterSection(
        defaultButton : IButtonComponentDecotator,
        defaultText: ITextComponentDecorator,
        color: ColorScheme
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // region - Divider Section -
            TextDivider(
                text = stringResource(id = R.string.or),
                color = color,
                modifier = Modifier.padding(
                    top = 16.dp
                ),
            )
            // endregion

            // region - Another Login Section -
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                // Google Login Button
                val googleButton = ButtonTextDecorator(
                    isParent = true,
                    text = stringResource(id = R.string.continue_with_google),
                    wrapped = ButtonOnClickDecorator(
                        customOnClick = {
                            /*TODO: Implement Google login*/
                            Log.d("Message", "Success login with google!")
                        },
                        wrapped = ButtonColorDecorator(
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = color.primary,
                                contentColor = color.onPrimary,
                                disabledContainerColor = color.onSurface,
                                disabledContentColor = color.surface
                            ),
                            wrapped = ButtonIsIconDecorator(
                                isIcon = true,
                                iconRes = R.drawable.ic_google,
                                wrapped = ButtonModifierDecorator(
                                    customModifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp)
                                        .border(
                                            width = 1.dp,
                                            color = color.secondary,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    wrapped = defaultButton
                                )
                            )
                        )
                    )
                )
                googleButton.Decorate {  }

                // Facebook Login Button
                val facebookButton = ButtonComponentBuilder()
                    .withConfig(
                        newConfig = googleButton.getConfig().copy(
                            text = stringResource(id = R.string.continue_with_facebook),
                            onClick = {
                                /*TODO: Implement Google login*/
                                Log.d("Message", "Success login with facebook!")
                            },
                            isIcon = true,
                            iconRes = R.drawable.ic_facebook,
                        )
                    )
                    .build()
                facebookButton.Decorate {  }
            }
            // endregion
        }


        // region - Sign Up Section -
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            val questionText = TextDecorator(
                isParent = true,
                text = stringResource(id = R.string.no_account),
                wrapped = defaultText
            )
            questionText.Decorate {  }

            val signUpButton = TextDecorator(
                isParent = true,
                text = stringResource(id = R.string.sign_up),
                wrapped = ModifierDecorator(
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .clickable(
                            onClick = { /*TODO: Implement Sign up*/ },
                            role = Role.Button
                        ),
                    wrapped = ColorDecorator(
                        color = color.secondary,
                        wrapped = defaultText
                    )
                )
            )
            signUpButton.Decorate {  }
        }
        // endregion
    }
    // endregion
}