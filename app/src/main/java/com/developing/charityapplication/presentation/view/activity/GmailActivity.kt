@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.activityEvent.EmailFormEvent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.AuthenticationViewModel
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.EmailFormViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel.AuthViewModel
import com.google.android.gms.common.internal.safeparcel.SafeParcelable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GmailActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            GmailFormUI()
        }
    }

    // endregion

    // region --- Methods ---

    @Composable
    fun GmailFormUI(){
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

    // region -- Main UI --
    @Composable
    fun GmailForm(){
        // region -- Default Value --
        val context = LocalContext.current

        val title = stringResource(id = R.string.email)
        // endregion

        // region -- ViewModel --
        val emailVM: EmailFormViewModel = hiltViewModel()
        val apiAuthVM: AuthViewModel = hiltViewModel()
        // endregion

        // region -- State --
        val state by emailVM.state.collectAsState()
        val emailResponse by apiAuthVM.emailResponse.collectAsState()
        // endregion

        // region -- Reset Data --
        LaunchedEffect(Unit) {
            emailVM.resetData()
            apiAuthVM.resetData()
        }
        // endregion

        // region -- Call Api --
        LaunchedEffect(true) {
            emailVM.validationEvents.collect { event ->
                if(event is EmailFormViewModel.ValidationEvent.Success){
                    val email = RequestEmailM(state.email)
                    apiAuthVM.sendOtp_ResetPassword(email)
                }
            }
        }
        // endregion

        // region -- Show Message --
        LaunchedEffect(emailResponse) {
            if (emailResponse?.code == StatusCode.SUCCESS.code && emailResponse?.result != null){
                onNavToAuthentication.putExtra("formType", 2)
                onNavToAuthentication.putExtra("email", state.email)
                startActivity(onNavToAuthentication)
                finish()
            }

            if(emailResponse != null && emailResponse?.code != StatusCode.SUCCESS.code){
                val sms = StatusCode.fromStatusResId(emailResponse?.code ?: 1)
                Toast.makeText(
                    context,
                    title + " " + sms,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        // endregion

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

                Body(
                    state = state.email,
                    stateError = state.emailError,
                    onChangeValue = { newValue ->
                        emailVM.onEvent(EmailFormEvent.EmailChange(newValue))
                    },
                    onSubmit = { emailVM.onEvent(EmailFormEvent.Submit) }
                )
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
    fun Body(
        state: String,
        stateError: String?,
        onChangeValue: (String) -> Unit,
        onSubmit: () -> Unit
    ){
        val textConfig = createTextDefault()
        val title = stringResource(id = R.string.email)

        // region - Input Gmail -
        InputFieldComponentBuilder()
            .withConfig(
                InputFieldConfig(
                    value = state,
                    onValueChange = { onChangeValue(it) },
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
                    isError = stateError != null,
                    supportText = {
                        if(stateError != null){
                            TextComponentBuilder()
                                .withConfig(
                                    textConfig.copy(
                                        text = title + " " + stateError,
                                        textStyle = AppTypography.labelMedium,
                                        color = AppColorTheme.onError
                                    )
                                )
                                .build()
                                .BaseDecorate {  }
                        }
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
            onClick = { onSubmit() },
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
    // endregion
    // endregion
    // endregion

    // endregion

    // region --- Fields ---

    private val onNavToPreviousActivity: Intent by lazy { Intent(this, LoginActivity::class.java) }
    private val onNavToAuthentication: Intent by lazy { Intent(this, AuthenticationActivity::class.java) }

    // endregion

}