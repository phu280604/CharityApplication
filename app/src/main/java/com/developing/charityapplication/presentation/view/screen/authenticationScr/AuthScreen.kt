package com.developing.charityapplication.presentation.view.screen.authenticationScr

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.event.activityEvent.AuthChangeEvent
import com.developing.charityapplication.presentation.state.activityState.AuthEventVM
import com.developing.charityapplication.presentation.state.activityState.AuthStateVM
import com.developing.charityapplication.presentation.state.activityState.AuthenticationState
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppTypography
import kotlinx.coroutines.delay

// region -- Authentication Screen --
@Composable
fun AuthScreen(
    states: AuthStateVM,
    onEventVM: AuthEventVM
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
    ) {

        AuthHeader()

        AuthBody(
            states = states.states,
            focusRequests = states.focusRequests,
            onChangeValue = onEventVM.onChangeValue
        )

        AuthFooter(
            onSendOtp = onEventVM.onSendOtp,
            onSubmit = onEventVM.onSubmit
        )
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
fun AuthBody(
    states: List<AuthenticationState>,
    focusRequests: List<FocusRequester>,
    onChangeValue: (Int, String) -> Unit
){
    val textConfig =  createConfigText()
    val textFieldConfig = createConfigInputFields()

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

            itemsIndexed(states) {
                index, item ->
                var countRequest = 1
                if (index >= states.count() - 1)
                    countRequest = 0

                InputFieldComponentBuilder()
                    .withConfig(
                        textFieldConfig.copy(
                            value = item.pinValue,
                            onValueChange = { newValue ->
                                if (newValue.length <= 1 && newValue.isDigitsOnly()) {
                                    onChangeValue(index, newValue)
                                    when {
                                        newValue.isNotEmpty() && index < states.lastIndex -> focusRequests[index + 1].requestFocus()
                                        newValue.isEmpty() && index > 0 -> focusRequests[index - 1].requestFocus()
                                    }
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            maxLine = 1,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(40.dp)
                                .focusRequester(focusRequests[index])
                                .focusProperties { next = focusRequests[index + countRequest] },
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
fun AuthFooter(
    onSendOtp: () -> Unit,
    onSubmit: () -> Unit,
    startSeconds: Int = 15
){
    val textConfig =  createConfigText()
    val buttonConfig =  createConfigButton()

    var timeLeft by remember{ mutableIntStateOf(startSeconds) }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft == startSeconds)
        {

            onSendOtp()
        }

        if (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    // region - Submit -
    ButtonComponentBuilder()
        .withConfig(
            buttonConfig.copy(
                text = stringResource(id = R.string.authentication_button),
                onClick = onSubmit,
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

        if (timeLeft != 0){
            TextComponentBuilder()
                .withConfig(
                    textConfig.copy(
                        text = timeLeft.toString(),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                )
                .build()
                .BaseDecorate {  }
        }
        else {
            TextComponentBuilder()
                .withConfig(
                    textConfig.copy(
                        text = stringResource(id = R.string.resend),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .clickable(
                                onClick = {
                                    timeLeft = startSeconds
                                    /*TODO: Implements resend pin logic*/
                                },
                                role = Role.Button
                            )
                    )
                )
                .build()
                .BaseDecorate {  }
        }
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