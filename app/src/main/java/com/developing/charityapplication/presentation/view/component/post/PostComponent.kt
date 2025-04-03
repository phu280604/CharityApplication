package com.developing.charityapplication.presentation.view.component.post

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.image.ImageConfig
import com.developing.charityapplication.presentation.view.component.image.builder.ImageComponentBuilder
import com.developing.charityapplication.presentation.view.component.post.decorator.IPostComponentDecorator
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.componentViewModel.postState.PostState


class PostComponent(
    private val config: PostConfig
) : IPostComponentDecorator {

    // region --- Overrides ---
    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Post()
    }

    override fun getConfig(): PostConfig = config
    
    // endregion

    // region --- Methods ---

    // Review
    @Composable
    fun PostPreview() {
        HeartBellTheme {
            /*  val config = PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "HopeFoundation",
                timeAgo = 2,
                content = "✨ Một bữa ăn - Ngàn nụ cười! ✨" +
                        "\n\nHôm nay, chúng tôi đã trao hơn 500 suất ăn miễn phí cho những " +
                        "người vô gia cư tại trung tâm thành phố.",
                donationValue = "2.5tr VND",
                dateRange = "12/01/2025 - 12/02/2025",
                likeCount = "234",
                commentCount = "234K",
                shareCount = "23K"
            )*/
            Post()
        }
    }

    // Main UI
    @Composable
    fun Post() {
        this.color = MaterialTheme.colorScheme
        this.typography = AppTypography

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column {
                // Post header
                PostHeader()

                // Post content
                PostBody()

                // Post actions
                PostFooter()
            }
        }
    }

    // Header Section
    @Composable
    fun PostHeader(){
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

            // region - More options -
            Box(
                modifier = Modifier.wrapContentSize()
            ){
                ButtonComponentBuilder()
                    .withConfig(
                        buttonConfigDefault.copy(
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_dots_vertical),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.fillMaxSize()
                                )
                            },
                            onClick = { postState.toggleExpendState() },
                            modifier = Modifier
                                .size(32.dp),
                        )
                    )
                    .build()
                    .BaseDecorate {  }

                OptionMenuLayout()
            }
            // endregion
        }
    }

    // Body Section
    @Composable
    fun PostBody(){
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // region - Post text -
            TextComponentBuilder()
                .withConfig(
                    textConfigDefault.copy(
                        text = config.content,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                    )
                )
                .build()
                .BaseDecorate {  }
            // endregion

            // region - Donation amount -
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                        shape = CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_donation),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = config.donationValue,
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            // endregion

            // region - Date range -
            Row(
                modifier = Modifier
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = 0.4f
                    ),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = config.dateRange,
                    style = AppTypography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(
                        alpha = 0.3f
                    )
                )
            }
            // endregion

            // region - Post image -
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(LightGray)
                    .padding(bottom = 12.dp)
            )
            // endregion
        }
    }

    // Footer Section
    @Composable
    fun PostFooter(){
        var listFooter = listOf(
            Pair(R.drawable.ic_love, R.string.like_post),
            Pair(R.drawable.ic_comment, R.string.comment_post),
            Pair(R.drawable.ic_share, R.string.share_post),
            Pair(R.drawable.ic_donation, R.string.donate_post)
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            itemsIndexed(listFooter){
                index, item ->
                ButtonComponentBuilder()
                    .withConfig(
                        buttonConfigDefault.copy(
                            shape = RectangleShape,
                            spacer = 4,
                            onClick = {
                                /*TODO: Implement function post logic*/
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = item.first),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                )
                            },
                            text = stringResource(id = item.second),
                            textStyle = AppTypography.labelMedium.copy(
                                fontWeight = FontWeight.Light
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(4.dp)
                        )
                    )
                    .build()
                    .BaseDecorate {  }
            }
        }

    }

    // Config Default
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
                    colors = ButtonColors(
                        containerColor = color!!.primary,
                        contentColor = color!!.onPrimary,
                        disabledContainerColor = color!!.surface,
                        disabledContentColor = color!!.onSurface
                    )
                )
            )
            .build()
            .getConfig()
    }

    // Option Layout
    @Composable
    fun OptionMenuLayout(){
        DropdownMenu(
            expanded = postState._expended,
            onDismissRequest = { if (postState._expended) postState.toggleExpendState() },
            modifier = Modifier
                .widthIn(80.dp, 192.dp)
                .heightIn(80.dp, 192.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 0.7f.dp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.End)
            ) {
                listItemMenu.forEachIndexed {
                        index, item ->
                    DropdownMenuItem(
                        onClick = { /*TODO: Implement drop down menu logic*/ },
                        text = {
                            Text(
                                text = stringResource(id = item.second),
                                style = typography!!.bodyMedium,
                                color = color!!.onPrimary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = item.first),
                                contentDescription = null,
                                tint = color!!.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                    if (index < listItemMenu.lastIndex)
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 1.dp,
                            color = color!!.surface
                        )
                }
            }
        }
    }

    // endregion

    // region --- Fields ---

    private val postState: PostState = PostState()

    private var color: ColorScheme? = null
    private var typography: Typography? = null

    private val listItemMenu = listOf(
        Pair(R.drawable.ic_bookmark, R.string.bookmark),
        Pair(R.drawable.ic_analysis, R.string.analysis),
        Pair(R.drawable.ic_report, R.string.report)
    )

    private val imageConfigDefault: ImageConfig by lazy { createImageDefault() }
    private val textConfigDefault: TextConfig by lazy { createTextDefault() }
    private val buttonConfigDefault: ButtonConfig by lazy { createButtonDefault() }

    // endregion

}