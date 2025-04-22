@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.identityModel.RequestCreateUser
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.activityEvent.RegisterFormEvent
import com.developing.charityapplication.presentation.state.activityState.RegisterFormState
import com.developing.charityapplication.presentation.view.screen.loading.LoadingScreen
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.RegisterFormViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.loading.LoadingViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormActivity: ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            RegisterScreen()
        }
    }

    // endregion

    // region --- Methods ---

    // region --- Register Screen ---
    @Composable
    fun RegisterScreen(){
        val isLoading by LoadingViewModel.isLoading.collectAsState()

        HeartBellTheme {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {},
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = AppColorTheme.primary
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    startActivity(onNavToLoginActivity)
                                    finish()
                                    /*TODO: Implement navigation register logic*/
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = AppColorTheme.onSurface
                                ),
                                modifier = Modifier
                                    .padding(start = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = null,
                                    tint = AppColorTheme.onPrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        },
                        actions = {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .size(40.dp)
                            )
                        },
                        modifier = Modifier
                            .background(
                                color = AppColorTheme.primary
                            )
                            .shadow(
                                elevation = 4.dp
                            )
                    )
                },
                modifier = Modifier
                    .zIndex(0f)
                    .windowInsetsPadding(WindowInsets.systemBars)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(
                            color = AppColorTheme.primary
                        )
                ){
                    RegisterForm()
                }
            }
            if(isLoading)
                LoadingScreen(modifier = Modifier.zIndex(1f))
        }
    }

    // region --- Register UI Form ---
    @Composable
    fun RegisterForm() {
        // region -- ViewModel --
        val userVM: UserViewModel = hiltViewModel()
        val registerVM: RegisterFormViewModel = hiltViewModel()
        // endregion

        // region - State Value -
        val userInfo by userVM.userInfo.collectAsState()
        val isLoading by LoadingViewModel.isLoading.collectAsState()

        val context = LocalContext.current
        // endregion

        val regisSuccessful: String = stringResource(id = R.string.sign_up)

        // region -- Calling API --
        LaunchedEffect(key1 = context) {
            registerVM.validationEvents.collect { event ->
                when(event){
                    is RegisterFormViewModel.ValidationEvent.Success -> {
                        setUserValue(registerVM.state)
                        userVM.createAccountUser(requestUser)
                    }
                }
            }
        }
        // endregion

        // region -- Loading Result --
        LaunchedEffect(isLoading) {
            if (!isLoading && userInfo != null) {
                val status = StatusCode.fromCode(userInfo?.code ?: 0)
                val text: String = StatusCode.fromStatusResId(status.statusResId)

                if(userInfo!!.code == StatusCode.SUCCESS.code) {
                    Toast.makeText(
                        context,
                        regisSuccessful + " " + text,
                        Toast.LENGTH_LONG
                    ).show()

                    onNavToAuthenticationActivity.putExtra("email", requestUser.email)
                    onNavToAuthenticationActivity.putExtra("username", requestUser.username)
                    onNavToAuthenticationActivity.putExtra("password", requestUser.password)
                    onNavToAuthenticationActivity.putExtra("formType", 3)

                    startActivity(onNavToAuthenticationActivity)
                    finish()
                }
                else{
                    Toast.makeText(
                        context,
                        text,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        // endregion

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ) {
            Header()


            Body(
                modifier = Modifier.weight(1f),
                regisVM = registerVM
            )

            Footer(
                regisVM = registerVM
            )
        }
    }

    // region --- Register UI Section ---
    @Composable
    fun Header(){
        val textConfig = createTextDefault()

        // region - Header Section -
        TextComponentBuilder()
            .withConfig(
                textConfig.copy(
                    text = stringResource(id = R.string.regis_form),
                    textStyle = AppTypography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            )
            .build()
            .BaseDecorate {  }
        // endregion
    }

    @Composable
    fun Body(
        modifier: Modifier,
        regisVM: RegisterFormViewModel
    ){
        // region - Component Config -
        val textConfig = createTextDefault()
        val textFieldConfig = createTextFieldDefault()
        // endregion

        // region - Remember Value -
        var passwordVisible by remember { mutableStateOf(List(2) {false}) }
        // endregion

        val state = regisVM.state

        // region - List of Label -
        val labelNameValues = listOf(
            R.string.last_name,
            R.string.first_name
        )
        val labelValues = listOf(
            R.string.username,
            R.string.email,
            R.string.password,
            R.string.repassword
        )
        // endregion

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region - Name Section -
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                labelNameValues.forEachIndexed{
                        index, item ->
                    val errorText = getErrorState(index, state)
                    val isError = errorText != null
                    InputFieldComponentBuilder()
                        .withConfig(
                            textFieldConfig.copy(
                                value = getState(index, state),
                                onValueChange = {
                                    setStateValue(regisVM, index, it)
                                },
                                label = {
                                    TextComponentBuilder()
                                        .withConfig(
                                            textConfig.copy(
                                                text = buildAnnotatedString {
                                                    withStyle(style = SpanStyle(color = Color.Black)) {
                                                        append(stringResource(id = item))
                                                    }
                                                    withStyle(style = SpanStyle(color = Color.Red)) {
                                                        append(" *")
                                                    }
                                                }.toString(),
                                                maxLine = 1
                                            )
                                        )
                                        .build()
                                        .BaseDecorate { }
                                },
                                isError = isError,
                                supportText = {
                                    if(isError){
                                        TextComponentBuilder()
                                            .withConfig(
                                                textConfig.copy(
                                                    text = "${stringResource(id = item)} ${errorText}",
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
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth()
                            )
                        )
                        .build()
                        .BaseDecorate {  }
                }
            }
            // endregion

            // region - Information Section -
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(labelValues){
                        index, item ->
                    val countHidePassword = index - (labelValues.count() - 2)
                    val countIndex = index + labelNameValues.count()
                    val errorText = getErrorState(countIndex, state)
                    val isError = errorText != null
                    InputFieldComponentBuilder()
                        .withConfig(
                            textFieldConfig.copy(
                                value = getState(countIndex, state),
                                onValueChange = {
                                    setStateValue(regisVM, countIndex, it)
                                },
                                label = {
                                    TextComponentBuilder()
                                        .withConfig(
                                            textConfig.copy(
                                                text = buildAnnotatedString {
                                                    withStyle(style = SpanStyle(color = Color.Black)) {
                                                        append(stringResource(id = item))
                                                    }
                                                    withStyle(style = SpanStyle(color = Color.Red)) {
                                                        append(" *")
                                                    }
                                                }.toString(),
                                                maxLine = 1
                                            )
                                        )
                                        .build()
                                        .BaseDecorate {  }
                                },
                                isError = isError,
                                supportText = {
                                    if(isError && item != R.string.password){
                                        TextComponentBuilder()
                                            .withConfig(
                                                textConfig.copy(
                                                    text = "${stringResource(id = item)} ${errorText}",
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
                                visualTransformation =
                                if (countHidePassword >= 0 && !passwordVisible[countHidePassword])
                                    PasswordVisualTransformation()
                                else
                                    VisualTransformation.None,
                                leadingIcon = {
                                    if(countHidePassword >= 0){
                                        IconButton(
                                            onClick = {
                                                val newValue = !passwordVisible[countHidePassword]
                                                passwordVisible = passwordVisible
                                                    .toMutableList()
                                                    .apply {
                                                        set(countHidePassword, newValue)
                                                    }
                                            }
                                        ) {
                                            Icon(
                                                painter = painterResource(
                                                    id = if (passwordVisible[countHidePassword]) R.drawable.ic_eye_open else R.drawable.ic_eye_closed
                                                ),
                                                contentDescription = null,
                                                tint = AppColorTheme.onPrimary
                                            )
                                        }
                                    }
                                    else null
                                },
                            )
                        )
                        .build()
                        .BaseDecorate {  }

                    if (index == labelValues.indexOf(R.string.password))
                        PasswordChecker(state.password)
                }
            }
            // endregion
        }
    }

    @Composable
    fun Footer(
        regisVM: RegisterFormViewModel
    ){
        // region - Component Config -
        val textConfig = createTextDefault()
        // endregion

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
        ) {
            // region - Sign up Button -
            Button(
                onClick = { regisVM.onEvent(RegisterFormEvent.Submit) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColorTheme.secondary,
                    contentColor = AppColorTheme.onSecondaryContainer
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = AppTypography.bodyMedium
                )
            }
            // endregion

            // region - Question Login Button -
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
            ) {
                val question = TextComponentBuilder()
                    .withConfig(
                        textConfig.copy(
                            text = stringResource(id = R.string.regis_question),
                            textStyle = AppTypography.bodyMedium,
                            color = AppColorTheme.onPrimary
                        )
                    )
                    .build()

                question.BaseDecorate {  }
                TextComponentBuilder()
                    .withConfig(
                        question.getConfig().copy(
                            text = stringResource(id = R.string.login),
                            color = AppColorTheme.secondary,
                            modifier = Modifier
                                .clickable(
                                    onClick = {
                                        startActivity(onNavToLoginActivity)
                                        finish()
                                    },
                                    role = Role.Button
                                )
                        )
                    )
                    .build()
                    .BaseDecorate {  }
            }
            // endregion
        }
    }

    // region -- Setter Value --
    fun setUserValue(state: RegisterFormState){
        requestUser.lastName = state.lastName
        requestUser.firstName = state.firstName
        requestUser.username = state.username
        requestUser.email = state.email
        requestUser.password = state.password
    }

    fun setStateValue(viewModel: RegisterFormViewModel, index: Int, value: String){
        when(index){
            0 -> viewModel.onEvent(RegisterFormEvent.LastNameChange(value))
            1 -> viewModel.onEvent(RegisterFormEvent.FirstNameChange(value))
            2 -> viewModel.onEvent(RegisterFormEvent.UsernameChange(value))
            3 -> viewModel.onEvent(RegisterFormEvent.EmailChange(value))
            4 -> viewModel.onEvent(RegisterFormEvent.PasswordChange(value))
            else -> viewModel.onEvent(RegisterFormEvent.RepeatedPasswordChange(value))
        }
    }

    fun getState(index: Int, state: RegisterFormState): String{
        when(index){
            0 -> return state.lastName
            1 -> return state.firstName
            2 -> return state.username
            3 -> return state.email
            4 -> return state.password
            else -> return state.repeatedPassword

        }
    }

    fun getErrorState(index: Int, state: RegisterFormState): String?{
        when(index){
            0 -> return state.lastNameError
            1 -> return state.firstNameError
            2 -> return state.usernameError
            3 -> return state.emailError
            4 -> return state.passwordError
            else -> return state.repeatedPasswordError

        }
    }

    // region -- Password Checker --
    @Composable
    fun PasswordElementChecker(
        icon: ImageVector,
        label: Int,
        color: Color
    ){
        Row(
            modifier = Modifier
                .padding(top = 4.dp)
                .wrapContentSize(),
            horizontalArrangement = Arrangement.Absolute.Right,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color
            )
            Text(
                text = stringResource(id = label),
                color = color,
                style = AppTypography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }
    }

    // region -- Checker Element --
    @Composable
    fun PasswordChecker(value: String){
        val elementCheckers = listOf(
            R.string.length_condi,
            R.string.caplock_condi,
            R.string.special_condi
        )

        Column (
            modifier = Modifier
                .offset(y = (-12).dp)
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            elementCheckers.forEachIndexed{
                    index, item ->

                var isCheck: Boolean = false

                when (index){
                    0 -> isCheck = isValidLength(value)
                    1 -> isCheck = hasUpperCase(value)
                    2 -> isCheck = hasSpecialCharacter(value)
                }

                val color = if (isCheck) AppColorTheme.secondary else AppColorTheme.onError
                val icon = Icons.Outlined.CheckCircle

                PasswordElementChecker(
                    icon = icon,
                    label = item,
                    color = color
                )
            }
        }
    }

    // region -- Condition Checker --
    fun isValidLength(value: String): Boolean {
        return value.length in 8..50
    }

    fun hasUpperCase(value: String): Boolean {
        return value.any { it.isUpperCase() }
    }

    fun hasSpecialCharacter(value: String): Boolean {
        val specialChars = "!@#\$%^&*(),.?\":{}|<>"
        return value.any { it in specialChars }
    }

    // region -- Component Default --
    @Composable
    fun createTextDefault() : TextConfig {
        return remember {
            TextComponentBuilder()
                .withConfig(
                    TextConfig(
                        color = AppColorTheme.onPrimary,
                        textStyle = AppTypography.titleMedium
                    )
                )
                .build()
                .getConfig()
        }
    }

    @Composable
    fun createTextFieldDefault(
        modifier: Modifier = Modifier
    ) : InputFieldConfig{
        return remember {
            InputFieldConfig(
                supportText = { /*TODO: Implement supportText*/ },
                maxLine = 1,
                shape = RoundedCornerShape(4.dp),
                modifier = modifier
                    .fillMaxWidth()
            )
        }
    }
    // endregion
    // endregion
    // endregion
    // endregion
    // endregion

    // endregion
    // endregion
    // endregion

    // endregion

    // region --- Fields ---

    private val onNavToLoginActivity: Intent by lazy { Intent(this, LoginActivity::class.java) }
    private val onNavToAuthenticationActivity: Intent by lazy { Intent(this, AuthenticationActivity::class.java) }

    private var requestUser: RequestCreateUser = RequestCreateUser()

    // endregion

}