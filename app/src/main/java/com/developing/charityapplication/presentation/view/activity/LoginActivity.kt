package com.developing.charityapplication.presentation.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.button.ButtonComponent
import com.developing.charityapplication.presentation.view.component.button.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.factory.ComponentFactory
import com.developing.charityapplication.presentation.view.component.factory.DefaultComponentFactory
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    var componentBuilder : ComponentFactory = DefaultComponentFactory()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            // Sử dụng rememberUpdatedState để lưu trữ logic của createDefaultButton
            val defaultButton = remember(MaterialTheme.colorScheme) {
                createDefaultButton(MaterialTheme.colorScheme)
            }

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

    @Composable
    fun createDefaultButton(colorScheme: ColorScheme) : ButtonComponent{
        return ButtonComponentBuilder()
            .withConfig(
                ButtonConfig(
                    colors = ButtonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.onSecondaryContainer,
                        disabledContainerColor = colorScheme.surface,
                        disabledContentColor = colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                )
            )
            .build()
    }


    // region -- Login Card --
    @Preview
    @Composable
    fun LoginCard(modifier: Modifier = Modifier) {
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

                BodySection()

                FooterSection()
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
                painter = painterResource(id = R.drawable.logo), // Replace with actual logo
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
    fun BodySection(){
        var username = remember { mutableStateOf("") }
        var password = remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        ) {
            // region - Input Field Section -
            var userConfig = createDefaultInputField(username)
            userConfig.modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)

            var passwordConfig = createDefaultInputField(password)
            passwordConfig.label = stringResource(id = R.string.password)
            passwordConfig.visualTransformation =
                if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation()
            passwordConfig.modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
            passwordConfig.leadingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                        ),
                        contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Username/Email Input
            componentBuilder.CreateOutlinedInputField(userConfig)

            // Password Input
            componentBuilder.CreateOutlinedInputField(passwordConfig)
            // endregion

            // region - Button Section -
            componentBuilder.CreateFilledButton(
                ButtonConfig(
                    text = stringResource(id = R.string.login),
                    onClick = { /*TODO: Implement login logic*/ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            )

            // Forgot Password
            componentBuilder.CreateTextButton(
                ButtonConfig(
                    text = stringResource(id = R.string.forgot_password),
                    onClick = { /*TODO: Implement forgot password logic*/ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.textButtonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,
                        contentColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.surface
                    ),
                    textStyle = AppTypography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(40.dp)
                )
            )
            // endregion
        }
    }

    @Composable
    fun createDefaultInputField(value: MutableState<String>) : InputFieldConfig{
        return InputFieldConfig(
            value = value.value,
            valueStyle = AppTypography.bodyMedium,
            onValueChange = { value.value = it },
            label = stringResource(id = R.string.username_email),
            labelStyle = AppTypography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.64f)
            ),
            shape = RoundedCornerShape(8.dp),
            color = OutlinedTextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.onBackground,
                    backgroundColor = MaterialTheme.colorScheme.background
                ),
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.64f),
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                errorTextColor = MaterialTheme.colorScheme.onError
            ),
            supportTextStyle = AppTypography.labelMedium,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = "User Icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        )

    }
    // endregion

    // region -- Footer Section --
    @Composable
    fun FooterSection(){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Divider
            TextDivider(
                text = stringResource(id = R.string.or),
                modifier = Modifier.padding(
                    top = 16.dp
                ),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                // Google Login Button
                var config = ButtonConfig(
                    text = stringResource(id = R.string.continue_with_google),
                    onClick = {  /*TODO: Implement Google login*/  },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.onSurface,
                        disabledContentColor = MaterialTheme.colorScheme.surface
                    ),
                    shape = RoundedCornerShape(8.dp),
                    isIcon = true,
                    iconRes = R.drawable.ic_google,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                )

                componentBuilder.CreateOutlinedButton(config)

                config.text = stringResource(id = R.string.continue_with_facebook)
                config.onClick = {  /*TODO: Implement Facebook login*/  }
                config.iconRes = R.drawable.ic_facebook
                componentBuilder.CreateOutlinedButton(config)
            }
        }

        // Sign Up Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            var config = ButtonConfig(
                text = stringResource(id = R.string.no_account),
                onClick = { },
                textStyle = AppTypography.bodyMedium,
                shape = RoundedCornerShape(8.dp),
                enable = false,
                colors = ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,
                    contentColor = MaterialTheme.colorScheme.secondary,
                    disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = MaterialTheme.colorScheme.primary
                ),
                contentPadding = PaddingValues(start = 8.dp)
            )

            componentBuilder.CreateTextButton(config)

            config.text = stringResource(id = R.string.sign_up)
            config.enable = true
            config.contentPadding = PaddingValues(start = 2.dp)
            config.onClick = { /*TODO: Implement Sign up*/ }
            componentBuilder.CreateTextButton(config)
        }

    }

    @Composable
    fun TextDivider(
        text: String,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.surface,
                thickness = 1.dp
            )

            Text(
                text = text,
                style = AppTypography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Divider(
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.surface,
                thickness = 1.dp
            )
        }
    }
    // endregion
}