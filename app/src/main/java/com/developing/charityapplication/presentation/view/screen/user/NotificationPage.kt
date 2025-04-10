@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.developing.charityapplication.presentation.view.component.notificationItem.NotificationConfig
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.notificationItem.builder.NotificationItemComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.UserAppViewModel

@Composable
fun HeaderNotification(navController: NavHostController){
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
                    text = stringResource(id = R.string.notification_page),
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
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        )

    }
}

@Composable
fun NotificationPageScreen(){
    val fakeDatas = fakeDatas()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxSize()
                .height(24.dp)
                .background(color = AppColorTheme.primary),
            contentAlignment = Alignment.Center
        ){
            AssistChip(
                label = {
                    Text(
                        text = stringResource(id = R.string.mark_as_read),
                        style = AppTypography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                },
                onClick = {
                    fakeDatas.forEach{
                        it.isRead.value = true
                    }
                },
                colors = AssistChipDefaults.assistChipColors().copy(
                    containerColor = AppColorTheme.surface,
                    labelColor = AppColorTheme.primary,
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = AppColorTheme.onPrimary
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(fakeDatas){
                    item ->
                NotificationItemComponentBuilder()
                    .withConfig(
                        item
                    )
                    .build()
                    .BaseDecorate {  }
            }
        }
    }
}

fun createNotification(
    username: String = "",
    background: Int = -1,
    content: String = "",
    timeStamp: String = ""
): NotificationConfig {
    val isReadState = mutableStateOf(false)
    return NotificationConfig(
        username = username,
        background = background,
        content = content,
        timeStamp = timeStamp,
        isRead = isReadState,
        onClick = {
            isReadState.value = true
        }
    )
}


fun fakeDatas(): List<NotificationConfig>{
    return listOf(
                createNotification(
                    username = "Trần Thị Mai",
                    background = R.drawable.avt_young_girl,
                    content = "đã bình luận bài viết của bạn.",
                    timeStamp = "5 phút trước"
                ),
                createNotification(
                    username = "Lê Hoàng Phúc",
                    background = R.drawable.avt_young_girl,
                    content = "đã thích bài viết của bạn.",
                    timeStamp = "10 phút trước"
                ),
                createNotification(
                    username = "Phạm Ngọc Lan",
                    background = R.drawable.avt_young_girl,
                    content = "đã bắt đầu theo dõi bạn.",
                    timeStamp = "20 phút trước"
                ),
                createNotification(
                    username = "Hệ thống",
                    background = R.drawable.logo,
                    content = "Tài khoản của bạn đã được xác minh thành công.",
                    timeStamp = "30 phút trước"
                ),
                createNotification(
                    username = "Đỗ Minh Khang",
                    background = R.drawable.avt_young_girl,
                    content = "đã bình luận bài viết của bạn.",
                    timeStamp = "1 giờ trước"
                ),
                createNotification(
                    username = "Hồ Thị Bích Vân",
                    background = R.drawable.avt_young_girl,
                    content = "đã chia sẻ bài viết của bạn.",
                    timeStamp = "1 giờ trước"
                ),
                createNotification(
                    username = "Vũ Quốc Huy",
                    background = R.drawable.avt_young_girl,
                    content = "đã thích bình luận của bạn.",
                    timeStamp = "2 giờ trước"
                ),
                createNotification(
                    username = "Bùi Thanh Hà",
                    background = R.drawable.avt_young_girl,
                    content = "đã gửi lời mời kết bạn.",
                    timeStamp = "2 giờ trước"
                ),
                createNotification(
                    username = "Hệ thống",
                    background = R.drawable.logo,
                    content = "Cập nhật ứng dụng mới đã sẵn sàng. Hãy tải ngay để trải nghiệm tính năng mới!",
                    timeStamp = "3 giờ trước"
                ),
                createNotification(
                    username = "Lý Gia Hân",
                    background = R.drawable.avt_young_girl,
                    content = "đã gắn thẻ bạn trong một bài viết.",
                    timeStamp = "4 giờ trước"
                )
            )
}