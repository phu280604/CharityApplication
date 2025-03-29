@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.activity

import android.content.Intent
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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.auth.RequestLoginAuthM
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        isForget = intent.getBooleanExtra("isForget", false)
        val userInfo = RequestLoginAuthM()
        userInfo.username = intent.getStringExtra("username") ?: ""
        userInfo.password = intent.getStringExtra("password") ?: ""
        Log.d("UserInfo", userInfo.username)
        Log.d("UserInfo", userInfo.password)

        setContent{
            MainUIPreview()
        }
    }

    // endregion

    // region --- Methods ---

    // region -- Main UI --
    @Composable
    fun PinEntryCard() {
        val textConfig =  createConfigText()
        val textFieldConfig = createConfigInputFields()
        val buttonConfig = createConfigButton()

        var pinValues by remember { mutableStateOf(List(5) { "" }) }
        val focusRequesters = remember { List(5) { FocusRequester() } }

        HeartBellTheme {
            Scaffold(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars),
                containerColor = MaterialTheme.colorScheme.primary,
                // Top app bar
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onNavToGmailActivity.putExtra("isForget", isForget)
                                    startActivity(onNavToGmailActivity)
                                    finish()
                                    /*TODO: Implement navigate back*/
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = AppColorTheme.onSurface
                                ),
                                modifier = Modifier.padding(start = 16.dp)
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
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = AppColorTheme.primary
                        ),
                        modifier = Modifier
                            .background(
                                color = AppColorTheme.primary
                            )
                            .shadow(
                                elevation = 4.dp
                            )
                    )
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    PinEntryCard()
                }
            }
        }

        Card(
            modifier = Modifier
                .offset(y = (-56).dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 0.72f.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // region - Information -
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    TextComponentBuilder()
                        .withConfig(
                            textConfig.copy(
                                text = stringResource(id = R.string.subtitle_authentication),
                                textStyle = AppTypography.titleMedium
                            )
                        )
                        .build()
                        .BaseDecorate {  }
                }
                // endregion

                // region - Pin Input -
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {

                    TextComponentBuilder()
                        .withConfig(
                            textConfig.copy(
                                text = stringResource(id = R.string.the_authentication_code),
                                textStyle = AppTypography.headlineSmall,
                                modifier = Modifier.padding()
                            )
                        )
                        .build()
                        .BaseDecorate {  }

                    LazyRow(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        itemsIndexed(pinValues) {
                            index, item ->

                            var countRequest = 1
                            if (index >= pinValues.count() - 1)
                                countRequest = 0

                            InputFieldComponentBuilder()
                                .withConfig(
                                    textFieldConfig.copy(
                                        value = item,
                                        onValueChange = { newValue ->
                                            if (newValue.length <= 1 && newValue.isDigitsOnly()) {
                                                val newPinValues = pinValues.toMutableList()
                                                newPinValues[index] = newValue
                                                pinValues = newPinValues

                                                if (newValue.isNotEmpty() && index < (pinValues.count() - 1)) {
                                                    focusRequesters[index + 1].requestFocus()
                                                }

                                                if(newValue.isEmpty() && index - 1 >= 0){
                                                    focusRequesters[index - 1].requestFocus()
                                                }
                                            }
                                        },
                                        shape = RoundedCornerShape(8.dp),
                                        maxLine = 1,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(40.dp)
                                            .focusRequester(focusRequesters[index])
                                            .focusProperties { next = focusRequesters[index + countRequest] },
                                        keyboardOptions = KeyboardOptions.Default.copy(
                                            keyboardType = KeyboardType.Number
                                        )
                                    )
                                )
                                .build()
                                .BaseDecorate {  }

                        }
                    }
                }
                // endregion

                // region - Submit -
                ButtonComponentBuilder()
                    .withConfig(
                        buttonConfig.copy(
                            text = stringResource(id = R.string.authentication_button),
                            onClick = {
                                startActivity(onNavToNextActivity)
                                finish()
                                /*TODO: Implement Submit Logic*/
                            },
                            textStyle = AppTypography.bodyMedium,
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .height(40.dp)
                                .fillMaxWidth(0.8f)
                        )
                    )
                    .build()
                    .BaseDecorate {  }
                // endregion

                // region - Resend -
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    TextComponentBuilder()
                        .withConfig(
                            textConfig.copy(
                                text = stringResource(id = R.string.question_authentication),
                            )
                        )
                        .build()
                        .BaseDecorate {  }

                    TextComponentBuilder()
                        .withConfig(
                            textConfig.copy(
                                text = stringResource(id = R.string.resend),
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .clickable(
                                        onClick = { /*TODO: Implements resend pin logic*/ },
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
    }

    // region -- Preview --
    @Preview
    @Composable
    fun MainUIPreview(){
        PinEntryCard()
    }

    // region -- Config Default Section --
    @Composable
    fun createConfigButton() : ButtonConfig {
        val colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            containerColor = MaterialTheme.colorScheme.secondary,
        )

        return remember {
            ButtonComponentBuilder()
                .withConfig(
                    ButtonConfig(
                        textStyle = AppTypography.bodyMedium,
                        colors = colors
                    )
                )
                .build()
                .getConfig()
        }
    }

    @Composable
    fun createConfigText() : TextConfig {
        val color = MaterialTheme.colorScheme.onPrimary

        return remember {
            TextComponentBuilder()
                .withConfig(
                    TextConfig(
                        textStyle = AppTypography.bodyMedium,
                        color = color
                    )
                )
                .build()
                .getConfig()
        }
    }

    @Composable
    fun createConfigInputFields() : InputFieldConfig {
        val color = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary.copy(
                alpha = 0.72f
            ),
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            selectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.onBackground,
                backgroundColor = MaterialTheme.colorScheme.background
            )
        )

        return remember {
            InputFieldComponentBuilder()
                .withConfig(
                    InputFieldConfig(
                        valueStyle = AppTypography.bodyMedium,
                        color = color,
                    )
                )
                .build()
                .getConfig()
        }
    }
    // endregion
    // endregion
    // endregion

    // endregion

    // region --- Fields ---

    private var isForget: Boolean = false

    private val onNavToGmailActivity: Intent by lazy { Intent(this, GmailActivity::class.java) }
    private val onNavToNextActivity: Intent by lazy {
        var nextClass: Class<*>
        if(isForget)
            nextClass = RecoveryActivity::class.java
        else
            nextClass = UserAppActivity::class.java
        Intent(this, nextClass)
    }

    // endregion

}