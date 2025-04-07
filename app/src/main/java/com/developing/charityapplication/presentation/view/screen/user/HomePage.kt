package com.developing.charityapplication.presentation.view.screen.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.post.builder.PostComponentBuilder

@Composable
fun HomePageScreen(){
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(scrollState)
    ){
        val postConfig = fakeDataPost()
        postConfig.forEachIndexed{
                index, item ->
            PostComponentBuilder()
                .withConfig(
                    item
                )
                .build()
                .BaseDecorate {  }
        }
    }
}

@Composable
fun fakeDataPost(): List<PostConfig>{
    return remember {
        listOf(
            PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "Nguyễn Văn A",
                timeAgo = 5,
                content = "Hôm nay là một ngày tuyệt vời! Cùng nhau làm điều ý nghĩa nhé! ❤️",
                donationValue = "500.000 VNĐ",
                dateRange = "1-5 Tháng 4",
                likeCount = "120",
                commentCount = "30",
                shareCount = "15"
            ),
            PostConfig(
                userbackground = R.drawable.avt_woman,
                username = "Trần Thị B",
                timeAgo = 10,
                content = "Chúng ta hãy chung tay giúp đỡ những hoàn cảnh khó khăn!",
                donationValue = "1.000.000 VNĐ",
                dateRange = "10-15 Tháng 4",
                likeCount = "250",
                commentCount = "50",
                shareCount = "30"
            ),
            PostConfig(
                userbackground = R.drawable.avt_woman,
                username = "Lê Văn C",
                timeAgo = 15,
                content = "Mỗi hành động nhỏ của bạn đều có thể tạo ra sự khác biệt lớn!",
                donationValue = "750.000 VNĐ",
                dateRange = "5-10 Tháng 4",
                likeCount = "180",
                commentCount = "40",
                shareCount = "20"
            ),
            PostConfig(
                userbackground = R.drawable.avt_young_boy,
                username = "Phạm Thị D",
                timeAgo = 20,
                content = "Cảm ơn mọi người đã luôn đồng hành và ủng hộ!",
                donationValue = "2.000.000 VNĐ",
                dateRange = "20-25 Tháng 4",
                likeCount = "400",
                commentCount = "100",
                shareCount = "50"
            )
        )
    }
}