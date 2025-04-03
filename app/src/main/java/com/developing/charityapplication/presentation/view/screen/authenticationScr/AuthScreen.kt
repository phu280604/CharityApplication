package com.developing.charityapplication.presentation.view.screen.authenticationScr

import android.app.Activity
import android.content.Intent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.event.activityEvent.AuthChangeEvent
import com.developing.charityapplication.presentation.view.activity.RecoveryActivity
import com.developing.charityapplication.presentation.view.activity.UserAppActivity
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.AuthenticationViewModel

// region -- Authentication Screen --
@Composable
fun AuthScreen(isForget: Boolean) {
    val authVM: AuthenticationViewModel = hiltViewModel()
    authVM.setState(5)

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {

        AuthHeader()

        AuthBody(authVM)

        AuthFooter(isForget)
    }
}
// endregion

// region -- Authentication Elements Screen --
@Composable
fun AuthHeader(){
    val textConfig =  createConfigText()
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
}

@Composable
fun AuthBody(authVM: AuthenticationViewModel){
    val textConfig =  createConfigText()
    val textFieldConfig = createConfigInputFields()

    val state by authVM.state.collectAsState()
    val focusRequest by authVM.focusRequest.collectAsState()

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

        // region - Pin Input -
        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            itemsIndexed(state) {
                    index, item ->

                var countRequest = 1
                if (index >= state.count() - 1)
                    countRequest = 0

                InputFieldComponentBuilder()
                    .withConfig(
                        textFieldConfig.copy(
                            value = state.get(index).pinValue,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && newValue.isDigitsOnly()) {
                                    authVM.onEvent(index, AuthChangeEvent.PinValueChange(newValue))

                                    if (newValue.isNotEmpty() && index < (state.count() - 1)) {
                                        focusRequest[index + 1].requestFocus()
                                    }

                                    if(newValue.isEmpty() && index - 1 >= 0){
                                        focusRequest[index - 1].requestFocus()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            maxLine = 1,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(40.dp)
                                .focusRequester(focusRequest[index])
                                .focusProperties { next = focusRequest[index + countRequest] },
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )
                    )
                    .build()
                    .BaseDecorate {  }

            }
        }
        // endregion
    }
}

@Composable
fun AuthFooter(isForget: Boolean){
    val textConfig =  createConfigText()
    val buttonConfig =  createConfigButton()

    val context = LocalContext.current

    // region - Submit -
    ButtonComponentBuilder()
        .withConfig(
            buttonConfig.copy(
                text = stringResource(id = R.string.authentication_button),
                onClick = {
                    val onNavToNextActivity = if(isForget)
                        Intent(context, RecoveryActivity::class.java)
                    else
                            Intent(context, UserAppActivity::class.java)
                    context.startActivity(onNavToNextActivity)
                    val activity = context as? Activity
                    activity?.finish()
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
// endregion

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
    return remember {
        InputFieldComponentBuilder()
            .withConfig(
                InputFieldConfig(
                    valueStyle = AppTypography.bodyMedium
                )
            )
            .build()
            .getConfig()
    }
}
// endregion