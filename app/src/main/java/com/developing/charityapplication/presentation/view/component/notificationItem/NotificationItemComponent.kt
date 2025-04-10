package com.developing.charityapplication.presentation.view.component.notificationItem


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.notificationItem.decorator.INotificationItemComponentDecorator
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography

class NotificationItemComponent(
    private val config: NotificationConfig
) : INotificationItemComponentDecorator {

    // region --- Overrides ---

    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp)
                .background(color = if (!config.isRead.value) AppColorTheme.onSurface else AppColorTheme.primary)
                .clickable(
                    role = Role.Button,
                    onClick = config.onClick
                )
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            // region -- Avatar User --
            Image(
                painter = if(config.background != -1)
                    painterResource(id = config.background)
                else painterResource(id = R.drawable.avt_young_girl),
                contentDescription = null,
                modifier = Modifier.weight(0.3f)
            )
            // endregion

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                // region - Title -
                val hightText = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontFamily = AppTypography.bodyMedium.fontFamily,
                            fontWeight = hightText.fontWeight,
                            fontSize = AppTypography.bodyMedium.fontSize
                        )){
                            append("${config.username} ")
                        }
                        withStyle(style = SpanStyle(
                            fontFamily = AppTypography.bodyMedium.fontFamily,
                            fontWeight = AppTypography.bodyMedium.fontWeight,
                            fontSize = AppTypography.bodyMedium.fontSize
                        )){
                            append(config.content)
                        }
                    },
                    color = AppColorTheme.onPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                )
                // endregion

                // region - TimeStamp -
                Text(
                    text = config.timeStamp,
                    style = AppTypography.labelMedium,
                    color = AppColorTheme.onPrimary.copy(alpha = 0.2f),
                    modifier = Modifier.weight(0.3f)
                )
                // endregion
            }
        }
    }

    override fun getConfig(): NotificationConfig {
        return config
    }

    // endregion
}