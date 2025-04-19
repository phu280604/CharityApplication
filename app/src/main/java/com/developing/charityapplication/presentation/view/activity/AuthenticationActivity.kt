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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.identityModel.RequestEmailM
import com.developing.charityapplication.domain.model.identityModel.RequestLoginM
import com.developing.charityapplication.domain.model.identityModel.RequestOTPM
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.activityEvent.AuthChangeEvent
import com.developing.charityapplication.presentation.state.activityState.AuthEventVM
import com.developing.charityapplication.presentation.state.activityState.AuthStateVM
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.screen.authenticationScr.AuthScreen
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.AuthenticationViewModel
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.RegisterFormViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.identityViewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        var formType = intent.getIntExtra("formType", 1)

        var email: RequestEmailM = RequestEmailM()
        email.email = intent.getStringExtra("email") ?: ""

        var account = RequestLoginM()
        account.username = intent.getStringExtra("username") ?: ""
        account.password = intent.getStringExtra("password") ?: ""

        setContent{
            PinEntryCard(
                formType = formType,
                email = email,
                account = account
            )
        }
    }

    // endregion

    // region --- Methods ---

    // region -- Main UI --
    @Composable
    fun PinEntryCard(
        formType: Int,
        email: RequestEmailM,
        account: RequestLoginM
    ) {
        // region -- View Model --
        val authVM: AuthenticationViewModel = hiltViewModel()
        val authApiVM: AuthViewModel = hiltViewModel()
        // endregion

        // region -- State --
        val state by authVM.state.collectAsState()
        val focusRequest by authVM.focusRequest.collectAsState()
        val checkingState by authVM.checkingState.collectAsState()
        val sendingResponse by authApiVM.sendingOtpResponse.collectAsState()
        val verifyingOtpRes by authApiVM.verifyingOtpResponse.collectAsState()
        val verifyingResetOtpRes by authApiVM.verifyingOtpResetPasswordResponse.collectAsState()
        val loginResponse by authApiVM.loginResponse.collectAsState()

        val context = LocalContext.current
        // endregion

        // region -- Message --
        val sendingOtp_success = stringResource(id = R.string.sendingOtp_success)
        val sendingOtp_failed = stringResource(id = R.string.sendingOtp_failed)
        val checkingState_sms = if (checkingState != 0) stringResource(id = checkingState) else ""

        val login_success = stringResource(id = R.string.verifying_success)
        val login_failed = stringResource(id = R.string.verifying_failed)
        // endregion

        // region -- Call API --
        LaunchedEffect(key1 = Unit) {
            authVM.validationEvents.collect { event ->
                when(event){
                    is AuthenticationViewModel.ValidationEvent.Success -> {
                        var otp = RequestOTPM()
                        state.forEach{ otp.otp += it.pinValue }
                        when(formType){
                            2 -> authApiVM.verifyOtpToResetPassword(otp.otp)
                            3 -> authApiVM.verifyOtp_Email(otp, account)
                        }
                    }
                }
            }
        }

        LaunchedEffect(key1 = checkingState) {
            checkingState.let {
                if (it != 0)
                    Toast.makeText(
                        context,
                        checkingState_sms,
                        Toast.LENGTH_LONG
                    ).show()
                authVM.resetState()
            }
        }

        LaunchedEffect(key1 = sendingResponse) {
            sendingResponse?.let {
                val sms = if (it.result?.result != null) sendingOtp_success else sendingOtp_failed
                Toast.makeText(context, sms, Toast.LENGTH_LONG).show()
                it.result = null
            }
        }

        LaunchedEffect(key1 = verifyingOtpRes) {
            verifyingOtpRes?.let {
                if (it.result?.result.isNullOrEmpty())
                    Toast.makeText(
                        context,
                        login_failed,
                        Toast.LENGTH_LONG
                    ).show()

                it.result = null
            }
        }

        LaunchedEffect(key1 = verifyingResetOtpRes) {
            verifyingResetOtpRes?.let {
                if (!it.result?.result.isNullOrEmpty())
                {
                    Toast.makeText(
                        context,
                        login_success,
                        Toast.LENGTH_LONG
                    ).show()

                    onNavToResetPasswordActivity.putExtra("resetToken", verifyingResetOtpRes?.result?.resetToken)
                    startActivity(onNavToResetPasswordActivity)

                    it.result = null

                    finish()
                }
                else{
                    val smsFailed = StatusCode.fromStatusResId(it.code)
                    Toast.makeText(
                        context,
                        smsFailed,
                        Toast.LENGTH_LONG
                    ).show()

                    it.result = null
                }
            }
        }

        LaunchedEffect(key1 = loginResponse) {
            loginResponse?.let {
                if (!it.result?.token.isNullOrEmpty())
                    Toast.makeText(
                        context,
                        login_success,
                        Toast.LENGTH_LONG
                    ).show()
                when(formType){
                    2 -> {
                        if(verifyingResetOtpRes?.result?.resetToken.isNullOrEmpty()) return@LaunchedEffect

                        onNavToResetPasswordActivity.putExtra("resetToken", verifyingResetOtpRes?.result?.resetToken)
                        startActivity(onNavToResetPasswordActivity)
                    }
                    else -> {
                        onNavToUserAppActivity.putExtra("isEnable", false)
                        startActivity(onNavToUserAppActivity)
                    }
                }
                it.result = null

                finish()
            }
        }
        // endregion

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
                                    val intent = when(formType){
                                        1 -> onNavToLoginActivity
                                        2 -> onNavToGmailActivity
                                        else -> onNavToRegisterActivity
                                    }
                                    intent.putExtra("formType", formType)
                                    startActivity(intent)
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
                    AuthScreen(
                        email = email.email,
                        formType = formType,
                        states = AuthStateVM(
                            states = state,
                            focusRequests = focusRequest,
                        ),
                        onEventVM = AuthEventVM(
                            onChangeValue = {
                                index, newValue ->
                                authVM.onEvent(index, AuthChangeEvent.PinValueChange(newValue))
                            },
                            onSendOtp = {
                                authApiVM.sendOtp_Email(email)
                            },
                            onSubmit = {
                                authVM.onEvent(formType, AuthChangeEvent.Submit)
                            }
                        )
                    )
                }
            }
        }
    }
    // endregion

    // endregion

    // region --- Fields ---

    private val onNavToUserAppActivity: Intent by lazy { Intent(this, UserAppActivity::class.java) }
    private val onNavToResetPasswordActivity: Intent by lazy { Intent(this, RecoveryActivity::class.java) }
    private val onNavToLoginActivity: Intent by lazy { Intent(this, LoginActivity::class.java) }
    private val onNavToRegisterActivity: Intent by lazy { Intent(this, RegisterFormActivity::class.java) }
    private val onNavToGmailActivity: Intent by lazy { Intent(this, GmailActivity::class.java) }

    // endregion

}