@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.donation

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.donationModel.RequestDonationM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.presentation.event.screenEvent.DonationEvent
import com.developing.charityapplication.presentation.view.activity.LoginActivity
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.EditProfilePage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.ProfilePage
import com.developing.charityapplication.presentation.view.screen.user.profile.MenuOpitionProfile
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.donation.DonationInfoViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.donation.ShareDonationInfoViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.FooterViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.HeaderViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.developing.charityapplication.infrastructure.utils.ConverterData.toVndCurrencyCompact
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.DonationDestinations
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations.HomePage
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.donationViewModel.DonationViewModel
import kotlinx.coroutines.flow.collect

@Composable
fun HeaderDonation(navController: NavHostController){
    // region -- List Title Top App Bar --
    val icon = painterResource(id = R.drawable.ic_donation)
    val title = stringResource(id = R.string.donate_post)
    // endregion

    // region -- Value ViewModel --
    // endregion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColorTheme.primary,
                titleContentColor = AppColorTheme.secondary.copy(alpha = 0.8f),
                navigationIconContentColor = AppColorTheme.secondary.copy(alpha = 0.8f),
            ),
            navigationIcon = {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
        )

    }
}

@Composable
fun DonationPage(
    shareDonationInfoVM: ShareDonationInfoViewModel,
    navController: NavHostController
){
    // region -- Default Value --
    val context = LocalContext.current

    val maxContentLength = 70

    val lovelySms = stringResource(R.string.content_content)
    // endregion

    // region -- ViewModel --
    val donationInfoVM: DonationInfoViewModel = hiltViewModel()
    val apiDonationVM: DonationViewModel = hiltViewModel()
    // endregion

    // region -- State --
    val sharedState by shareDonationInfoVM.shareState.collectAsState()

    val state by donationInfoVM.state.collectAsState()

    val donationResponse by apiDonationVM.donationResponse.collectAsState()

    var isPayment by remember { mutableStateOf(true) }
    // endregion

    // region -- SetUp Data --
    LaunchedEffect(Unit) {
        if(state.content.count() == 0){
            val message = sharedState.senderName + " " + lovelySms
            donationInfoVM.onEvent(DonationEvent.ContentChange(message))
        }
    }
    // endregion

    // region -- Call Api --
    LaunchedEffect(context) {
        donationInfoVM.validationEvents.collect{ event ->
            when(event){
                is DonationInfoViewModel.ValidationEvent.Success -> {
                    val requestDonation = RequestDonationM(
                        postId = sharedState.targetPostId,
                        donorId = sharedState.senderId,
                        amount = state.amount,
                        message = state.content
                    )
                    apiDonationVM.createDonation(requestDonation)
                }
            }
        }
    }
    // endregion

    // region -- Navigate to Payment --
    LaunchedEffect(donationResponse) {
        if(donationResponse?.result != null && isPayment){

            shareDonationInfoVM.addURL(donationResponse?.result?.payUrl ?: "")
            isPayment = false
            Log.d("URL", donationResponse?.result?.payUrl.toString())
            Log.d("URL", shareDonationInfoVM.sharePaymentState.value)
            navController.navigate(DonationDestinations.PaymentPage.route){
                popUpTo(HomePage.route){
                    inclusive = false
                }

                launchSingleTop = true
            }
        }
    }
    // endregion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AvatarAndNameDonation(
            avatar = sharedState.targetAvatar ?: painterResource(id = R.drawable.avt_default),
            name = sharedState.targetName,
            modifier = Modifier.weight(1f)
        )

        InfoDonation(
            state = listOf(state.amount, state.content),
            title = listOf(stringResource(R.string.amount_title), stringResource(R.string.content_title)),
            mainTitle = stringResource(R.string.amount_title),
            stateError = state.amountError,
            maxContentLength = maxContentLength,
            onChangeValue = { index, newValue ->
                when(index){
                    0 -> donationInfoVM.onEvent(DonationEvent.AmountChange(newValue))
                    else -> {
                        if(newValue.length <= maxContentLength)
                            donationInfoVM.onEvent(DonationEvent.ContentChange(newValue))
                    }
                }
            },
            modifier = Modifier.weight(1f)
        )

        ButtonDonation(
            onSubmit = { donationInfoVM.onEvent(DonationEvent.Submit) },
            modifier = Modifier.weight(0.3f)
        )
    }
}

@Composable
fun AvatarAndNameDonation(
    avatar: Painter,
    name: String,
    modifier: Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = avatar,
            contentDescription = "avatar",
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
                .border(width = 0.5f.dp, color = AppColorTheme.onPrimary, shape = CircleShape)
        )
        Text(
            text = name,
            style = AppTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            color = AppColorTheme.onPrimary,
            modifier = Modifier
                .weight(0.3f)
        )
    }
}

@Composable
fun InfoDonation(
    state: List<String>,
    mainTitle: String,
    title: List<String>,
    stateError: String?,
    maxContentLength: Int,
    onChangeValue: (Int, String) -> Unit,
    modifier: Modifier,
){
    var rawValue by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        state.forEachIndexed { index, value ->
            val displayValue = if (rawValue.toLongOrNull() != null && title[index] == mainTitle)
                rawValue.toLong().toVndCurrencyCompact()
            else value

            InputFieldComponentBuilder()
                .withConfig(
                    InputFieldConfig(
                        value = displayValue,
                        onValueChange = { input ->
                            rawValue = input.filter { it.isDigit() }
                            if(index == 0)
                                onChangeValue(index, rawValue)
                            else
                                onChangeValue(index, input)
                        },
                        maxLine = if(title[index] == mainTitle) 1 else Int.MAX_VALUE,
                        label = {
                            Text(
                                text = when(index){
                                    0 -> "${title[index]} *"
                                    else -> "${title[index]} (${value.length}/$maxContentLength)"
                                },
                                style = AppTypography.titleMedium
                            )
                        },
                        leadingIcon = {
                           if(title[index] == mainTitle)
                               Text(
                                   text = stringResource(R.string.currency),
                                   style = AppTypography.bodyMedium,
                                   color = AppColorTheme.onPrimary.copy(alpha = 0.8f)
                               )
                        },
                        isError = stateError != null && title[index] == mainTitle,
                        supportText = {
                            if(stateError != null && title[index] == mainTitle){
                                Text(
                                    text = stateError,
                                    style = AppTypography.labelMedium
                                )
                            }
                        },
                        keyboardOptions = if(title[index] == mainTitle) KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        ) else KeyboardOptions.Default,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                )
                .build()
                .BaseDecorate {  }
        }
    }
}

@Composable
fun ButtonDonation(
    onSubmit: () -> Unit,
    modifier: Modifier
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ){
        Button(
            onClick = { onSubmit() },
            modifier = modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = AppColorTheme.secondary,
                contentColor = AppColorTheme.onSecondaryContainer
            )
        ) {
            Text(
                text = stringResource(R.string.continue_donate),
                style = AppTypography.bodyMedium,
            )
        }
    }
}