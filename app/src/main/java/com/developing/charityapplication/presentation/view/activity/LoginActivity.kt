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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.infrastructure.utils.ShowSMS
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
import kotlin.code

@AndroidEntryPoint
class LoginActivity() : ComponentActivity() {
    
    // region --- Overrides ---
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            LoginScreen()
        }
    }
    
    // endregion

    // region --- Methods ---

    // region --- Login Screen ---
    @Composable
    fun LoginScreen(){
        // region -- View Model --
        val loginVM: LoginFormViewModel = hiltViewModel()
        val authVM: AuthViewModel = hiltViewModel()
        // endregion

        // region -- State value --
        val loginInfo by authVM.loginResponse.collectAsState()
        val isLoading by authVM.isLoading.collectAsState()

        val loginState = loginVM.state
        val context = LocalContext.current
        // endregion

        // region -- Show Notification --
        var showSms = remember { mutableStateOf(false) }
        var funcTitle by remember { mutableIntStateOf(0) }

        val loginSuccessful: String = stringResource(id = R.string.login)
        // endregion

        // region -- LaunchedEffect Call API --
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
        // endregion

        // region -- Loading Result --
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
        // endregion

        HeartBellTheme {
            Scaffold { innerPadding ->
                Card(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
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
                            textValue = listOf(loginState.username, loginState.password),
                            errorText = listOf(loginState.usernameError, loginState.passwordError),
                            onValuechange = {
                                index, value ->
                                setStateValue(
                                    viewModel = loginVM,
                                    index = index,
                                    value = value
                                )
                            },
                            onSubmit = {
                                loginVM.onEvent(LoginFormEvent.Submit)
                            }
                        )

                        FooterSection(
                            onClickGoogle = {
                                showSms.value = true
                                funcTitle = R.string.login_with_google
                            },
                            onClickFacebook = {
                                showSms.value = true
                                funcTitle = R.string.login_with_facebook
                            }
                        )
                    }
                }
            }
        }

        ShowSMS(
            funcTitle = if(funcTitle != 0) stringResource(funcTitle) else null,
            visible = showSms.value,
            onDismiss = { showSms.value = false }
        )
    }

    // region -- Login Screen Section --
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
    fun BodySection(
        textValue: List<String>,
        errorText: List<String?>,
        onValuechange: (Int, String) -> Unit,
        onSubmit: () -> Unit
    ){
        // region -- Component Config --
        val inputFieldDefault = createDefaultInputField()
        val textDefault = createDefaultText()
        val buttonDefault = createDefaultButton()
        // endregion

        // region -- Item Text Box --
        val itemInputfield = listOf(
            Pair(R.string.username_email, R.drawable.ic_user),
            Pair(R.string.password, R.drawable.ic_eye_open)
        )

        var passwordVisible by remember { mutableStateOf(true) }
        // endregion

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region - TextBox Section -
            itemInputfield.forEachIndexed {
                index, item ->
                val isError = errorText[index] != null
                InputFieldComponentBuilder()
                    .withConfig(
                        inputFieldDefault.copy(
                            value = textValue[index],
                            onValueChange = {
                                onValuechange(index, it)
                            },
                            label = {
                                Text(
                                    text = stringResource(item.first),
                                    style = AppTypography.titleMedium,
                                    color = AppColorTheme.onPrimary
                                )
                            },
                            visualTransformation =
                                if (passwordVisible)
                                    PasswordVisualTransformation()
                                else
                                    VisualTransformation.None,
                            leadingIcon = {
                                if (item.first == R.string.password){
                                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (!passwordVisible) item.second else R.drawable.ic_eye_closed
                                            ),
                                            contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                            tint = AppColorTheme.onPrimary
                                        )
                                    }
                                }
                                else{
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_user),
                                        contentDescription = "User Icon",
                                        tint = AppColorTheme.onPrimary
                                    )
                                }
                            },
                            isError = isError,
                            supportText = {
                                if(isError){
                                    TextComponentBuilder()
                                        .withConfig(
                                            textDefault.copy(
                                                text = "${stringResource(id = item.first)} ${errorText[index]}",
                                                textStyle = AppTypography.labelMedium.copy(
                                                    fontWeight = FontWeight.Light
                                                ),
                                                color = AppColorTheme.onError
                                            )
                                        )
                                        .build()
                                        .BaseDecorate {  }
                                }
                            },
                            modifier = if (item.first == R.string.password)
                                Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 4.dp,
                                        bottom = 24.dp
                                    )
                            else Modifier.fillMaxWidth()
                        )
                    )
                    .build()
                    .BaseDecorate {  }
            }
            // endregion

            // region - Login Button -
            ButtonComponentBuilder()
                .withConfig(
                    buttonDefault.copy(
                        text = stringResource(id = R.string.login),
                        onClick = {
                            onSubmit()
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
    fun FooterSection(
        onClickGoogle: () -> Unit,
        onClickFacebook: () -> Unit
    ){
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
                            onClick = {
                                onClickGoogle()
                                /*TODO: Implement Google login*/
                            },
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
                                onClickFacebook()
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

    // region -- Setter Value --
    fun setStateValue(viewModel: LoginFormViewModel, index: Int, value: String){
        when(index){
            0 -> viewModel.onEvent(LoginFormEvent.usernameChange(value))
            else -> viewModel.onEvent(LoginFormEvent.passwordChange(value))
        }
    }

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

    // region -- Login Preview --
    @Composable
    fun LoginScreenOverview(){
        LoginScreen()
    }
    // endregion
    // endregion
    // endregion
    // endregion
    // endregion

    // endregion

    // region --- Fields ---

    private val onNavToHomePage: Intent by lazy { Intent(this, UserAppActivity::class.java) }
    private val onNavToForgetPassword: Intent by lazy { Intent(this, GmailActivity::class.java) }
    private val onNavToRegister: Intent by lazy { Intent(this, RegisterFormActivity::class.java) }

    // endregion

}