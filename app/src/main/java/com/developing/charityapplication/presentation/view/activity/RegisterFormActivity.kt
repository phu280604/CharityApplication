@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.activity

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.RequestCreateUser
import com.developing.charityapplication.domain.model.ResponseModel
import com.developing.charityapplication.domain.model.UserModel
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.presentation.viewmodel.userViewModel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFormActivity: ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
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
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
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
            }
        }
    }

    // endregion

    // region --- Methods ---

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

    // region -- Condition Checker --
    fun isValidLength(value: String): Boolean {
        return value.length in 8..16
    }

    fun hasUpperCase(value: String): Boolean {
        return value.any { it.isUpperCase() }
    }

    fun hasSpecialCharacter(value: String): Boolean {
        val specialChars = "!@#\$%^&*(),.?\":{}|<>"
        return value.any { it in specialChars }
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

    // region -- Setter Value --
    fun setterValue(index: Int, value: String){
        when(index){
            0 -> requestUser.lastName = value
            1 -> requestUser.firstName = value
            2 -> requestUser.username = value
            3 -> requestUser.email = value
            4 -> requestUser.password = value
            else -> return
        }
    }

    fun checkerValue(index: Int, value: String): Int{
        var outOfRange: Int = 0
        var emptyField: Int = 0
        var emailFormat: Int = 0
        when(index){
            0 -> outOfRange = Checker.outOfRange(value = value, min = 3)
            1 -> outOfRange = Checker.outOfRange(value = value, min = 3)
            2 -> emptyField = Checker.containsBlank(value)
            3 -> {
                emptyField = Checker.containsBlank(value)
                emailFormat = Checker.isValidEmail(value)
            }
            4 -> {
                outOfRange = Checker.outOfRange(
                    value = value,
                    min = 8,
                    max = 16
                )

                if (!hasUpperCase(value)) return R.string.caplock_condi
                if (!hasSpecialCharacter(value)) return R.string.special_condi

            }
            5 -> if (value != requestUser.password) return R.string.error_not_match_field
            else -> return 0
        }
        if (outOfRange != 0) return outOfRange
        if (emptyField != 0) return emptyField
        if (emailFormat != 0) return emailFormat

        return 0
    }

    // region -- UI Section --
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
        validationTrigger: Boolean
    ){
        // region - Component Config -
        val textConfig = createTextDefault()
        val textFieldConfig = createTextFieldDefault()
        // endregion

        // region - Remember Value -
        var inputNameValues by remember { mutableStateOf(List(2) {""}) }
        var inputValues by remember { mutableStateOf(List(4) {""}) }
        var passwordVisible by remember { mutableStateOf(List(2) {false}) }
        // endregion

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
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
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
                inputNameValues.forEachIndexed{
                        index, item ->
                    InputFieldComponentBuilder()
                        .withConfig(
                            textFieldConfig.copy(
                                value = item,
                                onValueChange = {
                                    inputNameValues = inputNameValues
                                        .toMutableList()
                                        .apply {
                                            set(index, it)
                                        }
                                    setterValue(index, it)
                                },
                                label = {
                                    TextComponentBuilder()
                                        .withConfig(
                                            textConfig.copy(
                                                text = stringResource(id = labelNameValues[index])
                                            )
                                        )
                                        .build()
                                        .BaseDecorate { }
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
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(inputValues){
                        index, item ->
                    val countHidePassword = index - (labelValues.count() - 2)
                    InputFieldComponentBuilder()
                        .withConfig(
                            textFieldConfig.copy(
                                value = item,
                                onValueChange = {
                                    inputValues = inputValues
                                        .toMutableList()
                                        .apply {
                                            set(index, it)
                                        }
                                    setterValue(index + labelNameValues.count(), it)
                                },
                                label = {
                                    TextComponentBuilder()
                                        .withConfig(
                                            textConfig.copy(
                                                text = stringResource(id = labelValues[index])
                                            )
                                        )
                                        .build()
                                        .BaseDecorate {  }
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
                        PasswordChecker(inputValues[index])
                }
            }
            // endregion
        }
    }

    @Composable
    fun Footer(
        onValidation: () -> Unit,
        userVM: UserViewModel = hiltViewModel()
    ){
        // region - Component Config -
        val textConfig = createTextDefault()
        // endregion

        // region - State Value -
        val userInfo by userVM.userInfo.collectAsState()
        val isLoading by userVM.isLoading.collectAsState()
        // endregion

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            // region - Sign up Button -
            Button(
                onClick = {
                    onValidation()
                    userVM.createAccountUser(requestUser)
                          },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColorTheme.secondary,
                    contentColor = AppColorTheme.onSecondaryContainer
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = AppTypography.bodyMedium
                )
            }
        }

         LaunchedEffect(isLoading) {
             if (!isLoading && userInfo != null) {
                 startActivity(onNavToAuthenticationActivity)
                 finish()
             }
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
                                    /*TODO: Implement login logic*/
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

    // region -- Main UI --
    @Composable
    fun RegisterForm() {

        var validationTrigger by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
        ) {
            Header()

            Body(
                modifier = Modifier.weight(1f),
                validationTrigger = validationTrigger
            )

            Footer(
                onValidation = { validationTrigger = true }
            )
        }
    }

    // region -- Preview --
    @Preview(showBackground = true)
    @Composable
    fun RegisterFormPreview() {
        RegisterForm()
    }
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