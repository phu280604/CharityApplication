package com.developing.charityapplication.presentation.view.component.post

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.developing.charityapplication.presentation.view.theme.Green
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.image.ImageConfig
import com.developing.charityapplication.presentation.view.component.image.builder.ImageComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppTypography

class PostComponent {

    // region --- Methods ---

    // region -- Overview --
    @Preview(showBackground = true)
    @Composable
    fun PostPreview() {
        HeartBellTheme {
            val newConfig = PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "HopeFoundation",
                timeAgo = 2,
                content = "✨ Một bữa ăn - Ngàn nụ cười! ✨\n\nHôm nay, chúng tôi đã trao hơn 500 suất ăn miễn phí cho những người vô gia cư tại trung tâm thành phố.",
                donationValue = "2.5tr VND",
                dateRange = "12/01/2025 - 12/02/2025",
                likeCount = "234",
                commentCount = "234K",
                shareCount = "23K"
            )
            Post(
                newConfig
            )
        }
    }
    // endregion

    // region -- Main Component --
    @Composable
    fun Post(
        newConfig: PostConfig
    ) {
        this.color = MaterialTheme.colorScheme
        this.typography = AppTypography

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                // Post header
                PostHeader(newConfig)

                // Post content
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    // Post text
                    Text(
                        text = newConfig.content,
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Donation amount
                    Text(
                        text = newConfig.donationValue,
                        color = Green,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Date range
                    Text(
                        text = newConfig.dateRange,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Post image
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LightGray)
                            .padding(bottom = 12.dp)
                    )
                }

                Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)

                // Post actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Like
                    ActionItem(
                        icon = Icons.Default.Favorite,
                        count = newConfig.likeCount.toString(),
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Comment
                    ActionItem(
                        icon = Icons.Default.Favorite, // Using Favorite as placeholder for Comment
                        count = newConfig.commentCount.toString(),
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Share
                    ActionItem(
                        icon = Icons.Default.Share,
                        count = newConfig.shareCount.toString(),
                        onClick = { }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    // Donate
                    TextAction(text = "Quyên góp", onClick = { })

                    Spacer(modifier = Modifier.width(16.dp))

                    // Report
                    TextAction(text = "Báo cáo", onClick = { })
                }
            }
        }
    }
    // endregion

    // region -- Component Header Section --
    @Composable
    fun PostHeader(
        config: PostConfig
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row (
                modifier = Modifier
                    .wrapContentSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                // region - Profile picture -
                var userBackroundConfig = imageConfigDefault
                if(config.userbackground >= 0)
                    userBackroundConfig = userBackroundConfig.copy(
                        painter = config.userbackground
                    )
                ImageComponentBuilder()
                    .withConfig(
                        newConfig = userBackroundConfig
                    )
                    .build()
                    .BaseDecorate {  }
                // endregion

                // region - Username and time -
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                ) {
                    TextComponentBuilder()
                        .withConfig(
                            textConfigDefault.copy(
                                text = config.username,
                                textStyle = typography!!.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        )
                        .build()
                        .BaseDecorate {  }

                    TextComponentBuilder()
                        .withConfig(
                            textConfigDefault.copy(
                                text = "${config.timeAgo} ${stringResource(id = R.string.hour_ago)}",
                                textStyle = typography!!.labelMedium.copy(
                                    fontWeight = FontWeight.Light,
                                    color = color!!.onPrimary.copy(
                                        alpha = 0.32f
                                    )
                                )
                            )
                        )
                        .build()
                        .BaseDecorate {  }
                }
                // endregion
            }

            // More options
            ButtonComponentBuilder()
                .withConfig(
                    buttonConfigDefault.copy(
                        iconRes = R.drawable.ic_dots_vertical
                    )
                )
            IconButton(
                onClick = { },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color.Gray
                )
            }
        }
    }
    // endregion

    // region -- Sub-Component Section --

    fun createImageDefault(): ImageConfig {
        if (this.color == null) return ImageConfig()
        Log.d("DEBUG", "Drawable ID: ${R.drawable.avt_young_girl}")
        return ImageComponentBuilder()
            .withConfig(newConfig = ImageConfig(
                painter = R.drawable.avt_young_girl,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = this.color!!.secondary,
                        shape = CircleShape
                    )
            ))
            .build()
            .getConfig()
    }

    fun createTextDefault(): TextConfig {
        if (this.color == null || this.typography == null)
            return TextConfig()
        return TextComponentBuilder()
            .withConfig(
                newConfig = TextConfig(
                    textStyle = this.typography!!.bodyMedium,
                    color = this.color!!.onPrimary
                )
            )
            .build()
            .getConfig()
    }

    fun createButtonDefault(): ButtonConfig {
        if (this.color == null || this.typography == null)
            return ButtonConfig()
        return ButtonComponentBuilder()
            .withConfig(
                newConfig = ButtonConfig(
                    textStyle = this.typography!!.bodyMedium,
                )
            )
            .build()
            .getConfig()
    }

    // endregion
    @Composable
    fun ActionItem(
        icon: androidx.compose.ui.graphics.vector.ImageVector,
        count: String,
        onClick: () -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(end = 4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = count,
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }

    @Composable
    fun TextAction(
        text: String,
        onClick: () -> Unit
    ) {
        Text(
            text = text,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }

    // endregion

    // region --- Fields ---

    private var color: ColorScheme? = null
    private var typography: Typography? = null

    private val imageConfigDefault: ImageConfig by lazy { createImageDefault() }
    private val textConfigDefault: TextConfig by lazy { createTextDefault() }
    private val buttonConfigDefault: ButtonConfig by lazy { createButtonDefault() }

    // endregion
}