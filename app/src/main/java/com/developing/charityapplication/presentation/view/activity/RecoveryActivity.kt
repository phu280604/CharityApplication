@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecoveryActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            RecoveryPreview()
        }
    }

    // endregion

    // region --- Methods ---

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

    // region -- Checker Element --
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

    //  region -- Component Default --
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

    // region -- UI Section --
    @Composable
    fun Header(){
        val textConfig = createTextDefault()

        // region - Header Section -
        TextComponentBuilder()
            .withConfig(
                textConfig.copy(
                    text = stringResource(id = R.string.reset_password),
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
    fun Body() {
        val textConfig = createTextDefault()

        var inputValues by remember { mutableStateOf(List(2) {""}) }
        var passwordVisible by remember { mutableStateOf(List(2) {false}) }
        val labelValues = listOf(
            R.string.password,
            R.string.repassword
        )

        // region - Body Section -
        LazyColumn(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(inputValues){
                    index, item ->
                val countHidePassword = index - (labelValues.count() - 2)
                InputFieldComponentBuilder()
                    .withConfig(
                        InputFieldConfig(
                            value = item,
                            onValueChange = {
                                inputValues = inputValues
                                    .toMutableList()
                                    .apply {
                                        set(index, it)
                                    }
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
    }

    @Composable
    fun Footer() {
        // region - Footer Section -
        Button(
            onClick = {
                startActivity(onNavToHomePage)
                finish()
            /*TODO: Implement register logic*/
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColorTheme.secondary,
                contentColor = AppColorTheme.onSecondaryContainer
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.update_password),
                style = AppTypography.bodyMedium
            )
        }
        // endregion
    }

    // region -- Main UI --
    @Composable
    fun RecoveryUI(){
        HeartBellTheme {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { /*TODO: Implement title logic*/ },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = AppColorTheme.primary
                        ),
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onNavToGmailActivity.putExtra("isForget", true)
                                    startActivity(onNavToGmailActivity)
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
                    .windowInsetsPadding(WindowInsets.systemBars)
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(color = AppColorTheme.primary)
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                ){
                    Header()

                    Body()

                    Footer()
                }
            }
        }
    }

    // region -- Preview UI --
    @Preview
    @Composable
    fun RecoveryPreview(){
        RecoveryUI()
    }
    // endregion
    // endregion
    // endregion
    // endregion
    // endregion
    // endregion

    // endregion

    // region --- Fields ---

    private val onNavToGmailActivity: Intent by lazy { Intent(this, GmailActivity::class.java) }
    private val onNavToHomePage: Intent by lazy { Intent(this, UserAppActivity::class.java) }

    // endregion

}