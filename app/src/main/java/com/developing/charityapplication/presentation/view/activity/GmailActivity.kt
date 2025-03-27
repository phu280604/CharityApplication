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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GmailActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        isForget = intent.getBooleanExtra("isForget", false)

        setContent{
            PreviewGmail()
        }
    }

    // endregion

    // region --- Methods ---

    // region - Component Default -
    @Composable
    fun createTextDefault() : TextConfig{
        return remember {
            TextConfig(
                color = AppColorTheme.onPrimary,
                textStyle = AppTypography.bodyMedium
            )
        }
    }

    // region -- Main UI --
    @Composable
    fun GmailForm(){
        Card(
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = AppColorTheme.primary
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Header()

                Body()
            }
        }
    }

    // region -- UI Section --
    // Header Section
    @Composable
    fun Header(){
        val textConfig = createTextDefault()

        // region - Title -
        TextComponentBuilder()
            .withConfig(
                textConfig.copy(
                    text =  stringResource(id = R.string.title_authentication),
                    textStyle = AppTypography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            )
            .build()
            .BaseDecorate {  }
        // endregion

        // region - Subtitle -
        TextComponentBuilder()
            .withConfig(
                textConfig.copy(
                    text =  stringResource(id = R.string.email_require),
                    textStyle = AppTypography.bodyMedium,
                    color = AppColorTheme.surface,
                    modifier = Modifier.padding(top = 4.dp)
                )
            )
            .build()
            .BaseDecorate {  }
        // endregion
    }

    // Body Section
    @Composable
    fun Body(){
        var inputValue by remember { mutableStateOf("") }

        val textConfig = createTextDefault()

        // region - Input Gmail -
        InputFieldComponentBuilder()
            .withConfig(
                InputFieldConfig(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    shape = RoundedCornerShape(8.dp),
                    label = {
                        TextComponentBuilder()
                            .withConfig(
                                textConfig.copy(
                                    text = stringResource(id = R.string.email),
                                    textStyle = AppTypography.titleMedium
                                )
                            )
                            .build()
                            .BaseDecorate {  }
                    },
                    maxLine = 1,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
            )
            .build()
            .BaseDecorate {  }
        // endregion

        // region - Next Form -
        Button(
            onClick = {
                onNavToAuthentication.putExtra("isForget", isForget)
                startActivity(onNavToAuthentication)
                finish()
            /*TODO: Implement next activity logic*/
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AppColorTheme.secondary,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            TextComponentBuilder()
                .withConfig(
                    textConfig.copy(
                        text = stringResource(id = R.string.continues),
                        color = AppColorTheme.onSecondaryContainer
                    )
                )
                .build()
                .BaseDecorate {  }
        }
        // endregion
    }

    // region -- UI Preview --
    @Preview
    @Composable
    fun PreviewGmail(){
        HeartBellTheme {
            Scaffold(
                modifier = Modifier
                    .windowInsetsPadding(WindowInsets.systemBars),
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {},
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    startActivity(onNavToPreviousActivity)
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
                        .background(color = AppColorTheme.primary)
                ) {
                    GmailForm()
                }
            }
        }
    }
    // endregion
    // endregion
    // endregion
    // endregion

    // endregion

    // region --- Fields ---
    private var isForget: Boolean = false

    private val onNavToPreviousActivity: Intent by lazy {
        var previousActivity: Class<*>

        if (isForget)
            previousActivity = LoginActivity::class.java
        else
            previousActivity = RegisterFormActivity::class.java

        Intent(this, previousActivity)
    }
    private val onNavToAuthentication: Intent by lazy { Intent(this, AuthenticationActivity::class.java) }

    // endregion

}