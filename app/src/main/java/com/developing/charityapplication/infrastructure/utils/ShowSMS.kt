package com.developing.charityapplication.infrastructure.utils

import android.R.id.message
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import kotlinx.coroutines.delay

@Composable
fun ShowSMS(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Warning,
    message: String = stringResource(id = R.string.in_progress_sms),
    visible: Boolean,
    onDismiss: () -> Unit,
    durationMillis: Long = 1500,
){
    if (visible) {

        // region -- LauchedEffect When TimeOut --
        LaunchedEffect(Unit) {
            delay(durationMillis)
            onDismiss()
        }
        // endregion

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColorTheme.onPrimary.copy(alpha = 0.5f))
                .clickable(enabled = false) {
                    // Để ngăn interaction phía sau
                }
                .zIndex(1f)
        ) {
            // region - Content Notification -
            Column(
                modifier = modifier
                    .size(height = 176.dp, width = 288.dp)
                    .align(Alignment.Center)
                    .background(AppColorTheme.primary, RoundedCornerShape(16.dp))
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = AppColorTheme.secondary,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = message,
                    style = AppTypography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = AppColorTheme.onPrimary
                )
            }
            // endregion
        }
    }
}