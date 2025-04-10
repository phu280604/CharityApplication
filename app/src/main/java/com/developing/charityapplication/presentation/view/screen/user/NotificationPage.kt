@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.developing.charityapplication.presentation.view.component.notificationItem.NotificationConfig
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.UserAppViewModel

@Composable
fun HeaderNotification(navController: NavHostController){
    val listProfile = listOf(
        Pair(R.string.profile_page, Icons.Default.AccountCircle),
        Pair(R.string.edit_prodile, Icons.Default.Edit),
        Pair(R.string.more_option_profile, Icons.Default.List),
        Pair(R.string.logout, Icons.Default.ExitToApp),
    )

    val userAppVM: UserAppViewModel = UserAppViewModel()

    val state by userAppVM.selectedIndexState.asIntState()
    var showBottomSheet by remember { mutableStateOf(false) }

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
                    text = stringResource(listProfile[state].first),
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
                    imageVector = listProfile[state].second,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        showBottomSheet = true
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null
                    )
                    if (showBottomSheet)
                        MenuOpitionProfile(
                            listProfile,
                            onChangeState = { showBottomSheet = false },
                            onNavigate = { navItem -> /*TODO: Implement option profile navigate*/ }
                        )
                }
            },
        )

    }
}

@Composable
fun NotificationPageScreen(){

}

fun fakeDatas(){
    listOf(
        NotificationConfig(
            username = "Trần Thị Mai",
            background = R.drawable.avt_young_girl,
            content = "đã bình luận bài viết của bạn.",
            timeStamp = "5 phút trước"
        ),
        NotificationConfig(
            username = "Lê Hoàng Phúc",
            background = R.drawable.avt_young_girl,
            content = "đã thích bài viết của bạn.",
            timeStamp = "10 phút trước"
        ),
        NotificationConfig(
            username = "Phạm Ngọc Lan",
            background = R.drawable.avt_young_girl,
            content = "đã bắt đầu theo dõi bạn.",
            timeStamp = "20 phút trước"
        ),
        NotificationConfig(
            username = "Hệ thống",
            background = R.drawable.logo,
            content = "Tài khoản của bạn đã được xác minh thành công.",
            timeStamp = "30 phút trước"
        ),
        NotificationConfig(
            username = "Đỗ Minh Khang",
            background = R.drawable.avt_young_girl,
            content = "đã bình luận bài viết của bạn.",
            timeStamp = "1 giờ trước"
        ),
        NotificationConfig(
            username = "Hồ Thị Bích Vân",
            background = R.drawable.avt_young_girl,
            content = "đã chia sẻ bài viết của bạn.",
            timeStamp = "1 giờ trước"
        ),
        NotificationConfig(
            username = "Vũ Quốc Huy",
            background = R.drawable.avt_young_girl,
            content = "đã thích bình luận của bạn.",
            timeStamp = "2 giờ trước"
        ),
        NotificationConfig(
            username = "Bùi Thanh Hà",
            background = R.drawable.avt_young_girl,
            content = "đã gửi lời mời kết bạn.",
            timeStamp = "2 giờ trước"
        ),
        NotificationConfig(
            username = "Hệ thống",
            background = R.drawable.logo,
            content = "Cập nhật ứng dụng mới đã sẵn sàng. Hãy tải ngay để trải nghiệm tính năng mới!",
            timeStamp = "3 giờ trước"
        ),
        NotificationConfig(
            username = "Lý Gia Hân",
            background = R.drawable.avt_young_girl,
            content = "đã gắn thẻ bạn trong một bài viết.",
            timeStamp = "4 giờ trước"
        )
    )
}