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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            LoginScreenOverview()
        }
    }

    @Preview
    @Composable
    fun LoginScreenOverview(){
        HeartBellTheme {
            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(innerPadding)
                ) {
                    // region - Remember Theme -
                    color = MaterialTheme.colorScheme
                    typography = AppTypography
                    //endregion

                    LoginCard(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    )
                }

            }
        }
    }

    // region -- Login Card Fragment --
    // region -- Create Login Default Component --
    fun createDefaultButton() : ButtonConfig{
        return ButtonComponentBuilder()
            .withConfig(
                ButtonConfig(
                    colors = ButtonColors(
                        containerColor = color!!.secondary,
                        contentColor = color!!.onSecondaryContainer,
                        disabledContainerColor = color!!.surface,
                        disabledContentColor = color!!.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp),
                )
            )
            .build()
            .getConfig()
    }

    fun createDefaultInputField() : InputFieldConfig{
        return InputFieldComponentBuilder()
            .withConfig(
                InputFieldConfig(
                    valueStyle = typography!!.titleMedium,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
            )
            .build()
            .getConfig()
    }

    fun createDefaultText() : TextConfig{
        if (this.color == null || this.typography == null) return TextConfig()

        return TextComponentBuilder()
            .withConfig(
                TextConfig(
                    color = this.color!!.onPrimary,
                    textStyle = this.typography!!.bodyMedium
                )
            )
            .build()
            .getConfig()
    }
    // endregion

    @Composable
    fun LoginCard(
        modifier: Modifier = Modifier
    ) {
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
    fun BodySection(){
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
            val usernameInputField = InputFieldComponentBuilder()
                .withConfig(
                    inputFieldConfigDefault.copy(
                        value = username,
                        onValueChange = { username = it },
                        color = OutlinedTextFieldDefaults.colors(
                            cursorColor = color!!.onPrimary,
                            selectionColors = TextSelectionColors(
                                handleColor = color!!.onBackground,
                                backgroundColor = color!!.background
                            ),
                            unfocusedBorderColor = color!!.surface,
                            unfocusedTextColor = color!!.onPrimary,
                            focusedBorderColor = color!!.secondary,
                            focusedTextColor = color!!.onPrimary,
                            focusedLabelColor = color!!.onPrimary.copy(alpha = 0.8f),
                            unfocusedLabelColor = color!!.onPrimary.copy(alpha = 0.8f),
                            errorTextColor = color!!.onError
                        ),
                        label = {
                            Text(
                                text = stringResource(id = R.string.username_email),
                                style = typography!!.titleMedium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_user),
                                contentDescription = "User Icon",
                                tint = color!!.onPrimary
                            )
                        },
                    )
                )
                .build()
            usernameInputField.BaseDecorate {  }

            // Password Input Field
            val passwordInputField = InputFieldComponentBuilder()
                .withConfig(
                    usernameInputField.getConfig().copy(
                        value = password,
                        onValueChange = { password = it },
                        label = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = typography!!.titleMedium
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
                                    tint = color!!.onPrimary
                                )
                            }
                        }
                    )
                )
                .build()
            passwordInputField.BaseDecorate {  }
            // endregion

            // region - Button Section -
            // Login
            ButtonComponentBuilder()
                .withConfig(
                    buttonConfigDefault.copy(
                        text = stringResource(id = R.string.login),
                        onClick = { /*TODO: Implement login logic*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    )
                )
                .build()
                .BaseDecorate {  }

            // Forgot Password
            TextComponentBuilder()
                .withConfig(
                    newConfig = textConfigDefault.copy(
                        text = stringResource(id = R.string.forgot_password),
                        color = color!!.secondary,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable(
                                onClick = { /*TODO: Implement forgot password logic*/ },
                                role = Role.Button
                            ),
                        textAlign = TextAlign.Center,
                    )
                )
                .build()
                .BaseDecorate {  }
            // endregion
        }
    }
    // endregion

    // region -- Footer Section --
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
                color = color!!.surface,
                thickness = 1.dp
            )

            Text(
                text = text,
                style = AppTypography.bodyMedium.copy(
                    color = color!!.surface
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Divider(
                modifier = Modifier.weight(1f),
                color = color!!.surface,
                thickness = 1.dp
            )
        }
    }

    @Composable
    fun FooterSection(){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // region - Divider Section -
            TextDivider(
                text = stringResource(id = R.string.or),
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
                val googleButton = ButtonComponentBuilder()
                    .withConfig(
                        buttonConfigDefault.copy(
                            text = stringResource(id = R.string.continue_with_google),
                            onClick = { /*TODO: Implement Google login*/ },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = color!!.primary,
                                contentColor = color!!.onPrimary,
                                disabledContainerColor = color!!.onSurface,
                                disabledContentColor = color!!.surface
                            ),
                            imageRes = R.drawable.ic_google,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .border(
                                    width = 1.dp,
                                    color = color!!.secondary,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                        )
                    )
                    .build()
                googleButton.BaseDecorate {  }

                // Facebook Login Button
                ButtonComponentBuilder()
                    .withConfig(
                        newConfig = googleButton.getConfig().copy(
                            text = stringResource(id = R.string.continue_with_facebook),
                            onClick = {
                                /*TODO: Implement Google login*/
                                Log.d("Message", "Success login with facebook!")
                            },
                            imageRes = R.drawable.ic_facebook,
                        )
                    )
                    .build()
                    .BaseDecorate {  }
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
            TextComponentBuilder()
                .withConfig(
                    newConfig = textConfigDefault.copy(
                        text = stringResource(id = R.string.no_account),
                    )
                )
                .build()
                .BaseDecorate {  }

            TextComponentBuilder()
                .withConfig(
                    newConfig = textConfigDefault.copy(
                        text = stringResource(id = R.string.sign_up),
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .clickable(
                                onClick = { /*TODO: Implement Sign up*/ },
                                role = Role.Button
                            ),
                        color = color!!.secondary,
                    )
                )
                .build()
                .BaseDecorate {  }
        }
        // endregion
    }
    // endregion

    // region --- Fields ---

    private var color: ColorScheme? = null
    private var typography: Typography? = null

    private val textConfigDefault: TextConfig by lazy { createDefaultText() }
    private val buttonConfigDefault: ButtonConfig by lazy { createDefaultButton() }
    private val inputFieldConfigDefault: InputFieldConfig by lazy { createDefaultInputField() }

    // endregion
}