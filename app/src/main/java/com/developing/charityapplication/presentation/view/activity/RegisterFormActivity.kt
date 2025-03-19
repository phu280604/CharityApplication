package com.developing.charityapplication.presentation.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R

class RegisterFormActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            RegisterFormPreview()
        }
    }

    @Composable
    fun RegisterForm() {
        // State for form fields
        val textFieldConfig = createTextFieldDefault()
        val textConfig = createTextDefault()

        var inputValues by remember { mutableStateOf(List(5) {""}) }
        val labelValues = listOf(
            R.string.name,
            R.string.email,
            R.string.username,
            R.string.password,
            R.string.repassword
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = AppColorTheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 0.56f.dp,
                    color = AppColorTheme.onPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region - Header Section -
            TextComponentBuilder()
                .withConfig(
                    textConfig.copy(
                        text = stringResource(id = R.string.regis_form),
                        textStyle = AppTypography.headlineMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)

                    )
                )
                .build()
                .BaseDecorate {  }
            // endregion

            // region - Body Section -
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(inputValues){
                    index, item ->
                    InputFieldComponentBuilder()
                        .withConfig(
                            textFieldConfig.copy(
                                value = item,
                                onValueChange = {
                                    inputValues = inputValues.toMutableList().apply { set(index, it) }
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
                                supportText = { /*TODO: Implement supportText*/ },
                                maxLine = 1,
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        )
                        .build()
                        .BaseDecorate {  }

                    if (index == labelValues.indexOf(R.string.password))
                        PasswordChecker(inputValues[index])
                }
            }
            // endregion

            // region - Footer Section -
            Button(
                onClick = { /*TODO: Implement register logic*/ },
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
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
                                    onClick = { /*TODO: Implement login logic*/ },
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

    @Composable
    fun PasswordChecker(value: String){
        val elementCheckers = listOf(
            R.string.length_condi,
            R.string.caplock_condi,
            R.string.special_condi
        )

        Column (
            modifier = Modifier
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

    @Composable
    fun PasswordElementChecker(
        icon: ImageVector,
        label: Int,
        color: Color
        ){
        Row(
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.Absolute.Right
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
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }

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

    // region --- Creator Component ---

    @Composable
    fun createTextFieldDefault() : InputFieldConfig {
        val colorTextField = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = AppColorTheme.primary,
            focusedTextColor = AppColorTheme.onPrimary,
            focusedBorderColor = AppColorTheme.onPrimary,
            unfocusedContainerColor = AppColorTheme.primary,
            unfocusedTextColor = AppColorTheme.onPrimary,
            unfocusedBorderColor = AppColorTheme.onPrimary,
            cursorColor = AppColorTheme.onPrimary,
            selectionColors = TextSelectionColors(
                handleColor = AppColorTheme.onBackground,
                backgroundColor = AppColorTheme.background
            )
        )
        return remember {
            InputFieldComponentBuilder()
                .withConfig(
                    InputFieldConfig(
                        color = colorTextField
                    )
                )
                .build()
                .getConfig()
        }
    }

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

    // endregion

    // Preview function for development
    @Preview
    @Composable
    fun RegisterFormPreview() {
        HeartBellTheme {
            Scaffold(
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
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