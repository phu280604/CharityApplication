@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.donation

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.donation.DonationInfoViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.donation.ShareDonationInfoViewModel
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.FooterViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.HeaderViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Base64

@Composable
fun HeaderPayment(navController: NavHostController) {
    val icon = painterResource(id = R.drawable.ic_payment)
    val title = stringResource(id = R.string.payment_title)

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
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(AppColorTheme.secondary.copy(alpha = 0.8f))
                )
            },
        )
    }
}

@Composable
fun PaymentPage(
    shareDonationInfoVM: ShareDonationInfoViewModel,
    navController: NavHostController
) {
    val urlPayment by shareDonationInfoVM.sharePaymentState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoMoQRCodeScreen(
            modifier = Modifier.weight(1f),
            url = urlPayment
        )
        CheckOutButton(
            modifier = Modifier.weight(0.2f),
            onClick = {
                HeaderViewModel.changeSelectedIndex()
                FooterViewModel.changeSelectedIndex()

                navController.navigate(HomeDestinations.HomePage.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        )

    }
}

@Composable
fun MoMoQRCodeScreen(
    modifier: Modifier,
    url: String
) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.userAgentString = "Mozilla/5.0 (Android)"
            loadUrl(url)
        }
    }, modifier = modifier)
}

@Composable
fun CheckOutButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = AppColorTheme.secondary,
                contentColor = AppColorTheme.onSecondaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                stringResource(R.string.checkout_payment),
                style = AppTypography.bodyMedium,
            )
        }
    }
}