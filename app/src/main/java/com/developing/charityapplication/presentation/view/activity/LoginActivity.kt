package com.developing.charityapplication.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.activityEvent.LoginFormEvent
import com.developing.charityapplication.presentation.state.activityState.LoginFormState
import com.developing.charityapplication.presentation.state.activityState.RegisterFormState
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.LoginFormViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class LoginActivity() : ComponentActivity() {
    
    // region --- Overrides ---
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            LoginScreenOverview()
        }
    }
    
    // endregion

    // region --- Methods ---
    
    // region -- Component Default ---
    @Composable
    fun createDefaultButton() : ButtonConfig{
        return remember {
            ButtonConfig(
                colors = ButtonColors(
                    containerColor = AppColorTheme.secondary,
                    contentColor = AppColorTheme.onSecondaryContainer,
                    disabledContainerColor = AppColorTheme.surface,
                    disabledContentColor = AppColorTheme.onSurface
                ),
                shape = RoundedCornerShape(8.dp),
            )
        }
    }

    @Composable
    fun createDefaultOutlinedButton() : ButtonConfig{
        return remember {
            ButtonConfig(
                colors = ButtonColors(
                    containerColor = AppColorTheme.primary,
                    contentColor = AppColorTheme.onPrimary,
                    disabledContainerColor = AppColorTheme.onSurface,
                    disabledContentColor = AppColorTheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .border(
                        width = 1.dp,
                        color = AppColorTheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }

    @Composable
    fun createDefaultInputField() : InputFieldConfig{
        return remember {
            InputFieldConfig(
                valueStyle = AppTypography.titleMedium,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }

    @Composable
    fun createDefaultText() : TextConfig{
        return remember {
            TextConfig(
                color = AppColorTheme.onPrimary,
                textStyle = AppTypography.bodyMedium
            )
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
                color = AppColorTheme.surface,
                thickness = 1.dp
            )

            Text(
                text = text,
                style = AppTypography.bodyMedium.copy(
                    color = AppColorTheme.surface
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Divider(
                modifier = Modifier.weight(1f),
                color = AppColorTheme.surface,
                thickness = 1.dp
            )
        }
    }

    // region -- UI Section --
    @Composable
    fun HeaderSection(){
        // region - Title Section -
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
        // endregion
    }

    @Composable
    fun BodySection(){
        val inputFieldDefault = createDefaultInputField()
        val textDefault = createDefaultText()
        val buttonDefault = createDefaultButton()

        // region - View Model -
        val loginVM: LoginFormViewModel = hiltViewModel()
        val authVM: AuthViewModel = hiltViewModel()
        // endregion

        // region - State value -
        val loginInfo by authVM.loginResponse.collectAsState()
        val isLoading by authVM.isLoading.collectAsState()

        val loginState = loginVM.state
        val context = LocalContext.current
        // endregion

        var passwordVisible by remember { mutableStateOf(false) }

        val loginSuccessful: String = stringResource(id = R.string.login)

        LaunchedEffect(Unit) {
            loginVM.validationEvents.collect { event ->
                if (event is LoginFormViewModel.ValidationEvent.Success) {
                    val request = RequestLoginM(
                        username = loginVM.state.username,
                        password = loginVM.state.password
                    )
                    authVM.defaultLogin(request)
                }
            }
        }

        LaunchedEffect(isLoading) {
            loginInfo?.let {
                val status = StatusCode.fromCode(it.code)
                val message = StatusCode.fromStatusResId(status.statusResId)

                if (!isLoading) {
                    if (it.code == 1000) {
                        Toast.makeText(
                            context,
                            "$loginSuccessful: $message",
                            Toast.LENGTH_LONG
                        ).show()
                        startActivity(onNavToHomePage)
                        finish()
                    } else {
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region - Username/Email Input Field -
            val usernameInputField = InputFieldComponentBuilder()
                .withConfig(
                    inputFieldDefault.copy(
                        value = loginState.username,
                        onValueChange = {
                            loginVM.onEvent(LoginFormEvent.usernameChange(it))
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.username_email),
                                style = AppTypography.titleMedium,
                                color = AppColorTheme.onPrimary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_user),
                                contentDescription = "User Icon",
                                tint = AppColorTheme.onPrimary
                            )
                        },
                    )
                )
                .build()
            usernameInputField.BaseDecorate {  }
            // endregion

            // region - Password Input Field -
            val passwordInputField = InputFieldComponentBuilder()
                .withConfig(
                    usernameInputField.getConfig().copy(
                        value = loginState.password,
                        onValueChange = {
                            loginVM.onEvent(LoginFormEvent.passwordChange(it))
                        },
                        label = {
                            Text(
                                text = stringResource(id = R.string.password),
                                style = AppTypography.titleMedium,
                                color = AppColorTheme.onPrimary
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
                                    tint = AppColorTheme.onPrimary
                                )
                            }
                        }
                    )
                )
                .build()
            passwordInputField.BaseDecorate {  }
            // endregion

            // region - Login Button -
            ButtonComponentBuilder()
                .withConfig(
                    buttonDefault.copy(
                        text = stringResource(id = R.string.login),
                        onClick = {
                            loginVM.onEvent(LoginFormEvent.Submit)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    )
                )
                .build()
                .BaseDecorate {  }
            // endregion

            // region - Forget Password Button -
            TextComponentBuilder()
                .withConfig(
                    textDefault.copy(
                        text = stringResource(id = R.string.forgot_password),
                        color = AppColorTheme.secondary,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable(
                                onClick = {
                                    onNavToForgetPassword.putExtra("isForget", true)
                                    startActivity(onNavToForgetPassword)
                                    finish()
                                /*TODO: Implement forgot password logic*/
                                },
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

    @Composable
    fun FooterSection(){
        val buttonDefault = createDefaultOutlinedButton()
        val textDefault = createDefaultText()

        // region - Other Login Section -
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
                // region - Google Login Button -
                val googleButton = ButtonComponentBuilder()
                    .withConfig(
                        buttonDefault.copy(
                            text = stringResource(id = R.string.continue_with_google),
                            onClick = { /*TODO: Implement Google login*/ },
                            content = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_google),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )
                    )
                    .build()
                googleButton.BaseDecorate {  }
                // endregion

                // region - Facebook Login Button -
                ButtonComponentBuilder()
                    .withConfig(
                        newConfig = googleButton.getConfig().copy(
                            text = stringResource(id = R.string.continue_with_facebook),
                            onClick = {
                                /*TODO: Implement Google login*/
                                Log.d("Message", "Success login with facebook!")
                            },
                            content = {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_facebook),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )
                    )
                    .build()
                    .BaseDecorate {  }
                // endregion
            }
            // endregion
        }
        // endregion

        // region - Sign Up Section -
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            // region - Question Text -
            TextComponentBuilder()
                .withConfig(
                    textDefault.copy(
                        text = stringResource(id = R.string.no_account),
                    )
                )
                .build()
                .BaseDecorate {  }
            // endregion

            // region - Register Text Button -
            TextComponentBuilder()
                .withConfig(
                    textDefault.copy(
                        text = stringResource(id = R.string.sign_up),
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .clickable(
                                onClick = {
                                    startActivity(onNavToRegister)
                                    finish()
                                /*TODO: Implement Sign up*/
                                },
                                role = Role.Button
                            ),
                        color = AppColorTheme.secondary,
                    )
                )
                .build()
                .BaseDecorate {  }
            // endregion
        }
        // endregion
    }

    // region -- Login Card --
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

    // region -- Login Preview --
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
                    LoginCard(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxSize()
                    )
                }

            }
        }
    }
    // endregion
    // endregion
    // endregion
    // endregion

    // region -- Setter Value --

    fun setLoginValue(state: LoginFormState){
        requestManualLogin.username = state.username
        requestManualLogin.password = state.password
    }

    fun setStateValue(viewModel: LoginFormViewModel, index: Int, value: String){
        when(index){
            0 -> viewModel.onEvent(LoginFormEvent.usernameChange(value))
            else -> viewModel.onEvent(LoginFormEvent.passwordChange(value))
        }
    }

    fun getState(index: Int, state: LoginFormState): String{
        when(index){
            0 -> return state.username
            else -> return state.password
        }
    }

    fun setErrorState(index: Int, state: LoginFormState): String?{
        when(index){
            0 -> return state.usernameError
            else -> return state.passwordError
        }
    }

    // endregion

    // endregion

    // region --- Fields ---

    private val onNavToHomePage: Intent by lazy { Intent(this, UserAppActivity::class.java) }
    private val onNavToForgetPassword: Intent by lazy { Intent(this, GmailActivity::class.java) }
    private val onNavToRegister: Intent by lazy { Intent(this, RegisterFormActivity::class.java) }

    private val requestManualLogin: RequestLoginM = RequestLoginM()
    // endregion

}