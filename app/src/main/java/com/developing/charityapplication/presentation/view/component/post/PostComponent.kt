package com.developing.charityapplication.presentation.view.component.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.image.ImageConfig
import com.developing.charityapplication.presentation.view.component.image.builder.ImageComponentBuilder
import com.developing.charityapplication.presentation.view.component.post.decorator.IPostComponentDecorator
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.componentViewModel.postState.DropDownMenuState
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period


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

    // Main UI
    @Composable
    fun Post() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RectangleShape
        ) {
            Column {
                // Post header
                PostHeader()

                // Post content
                PostBody()

                // Post actions
                PostFooter()

                HorizontalDivider(
                    thickness = 1.dp,
                    color = AppColorTheme.surface
                )
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
                var userBackroundConfig = createImageDefault()
                ImageComponentBuilder()
                    .withConfig(
                        userBackroundConfig.copy(painter = config.userbackground)
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
                                textStyle = AppTypography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        )
                        .build()
                        .BaseDecorate {  }

                    TextComponentBuilder()
                        .withConfig(
                            textConfigDefault.copy(
                                text = getTimeAgo(createAt = config.timeAgo ?: LocalDateTime.now()),
                                textStyle = AppTypography.labelMedium.copy(
                                    fontWeight = FontWeight.Light,
                                    color = AppColorTheme.onPrimary.copy(
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
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = AppColorTheme.primary)
                    .padding(bottom = 12.dp)
            ){
                itemsIndexed(config.fileIds){
                    index, item ->
                    Box(
                        modifier = Modifier
                            .width(112.dp)
                            .fillMaxHeight()
                            .background(
                                color = AppColorTheme.onPrimary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .border(
                                width = 0.5f.dp,
                                color = AppColorTheme.onPrimary.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .padding(8.dp),
                        contentAlignment = Alignment.TopEnd
                    ){
                        // region - Show Image -
                        Image(
                            painter = rememberAsyncImagePainter(item),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
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

    @Composable
    fun getTimeAgo(createAt: LocalDateTime, currentDateTime: LocalDateTime = LocalDateTime.now()): String {
        val duration = Duration.between(createAt, currentDateTime)
        val period = Period.between(createAt.toLocalDate(), currentDateTime.toLocalDate())

        var timeLeft = when {
            duration.toMinutes() < 1 -> "${duration.seconds} ${stringResource(id = R.string.second)}"
            duration.toHours() < 1 -> "${duration.toMinutes()} ${stringResource(id = R.string.minute)}"
            duration.toHours() < 24 -> "${duration.toHours()} ${stringResource(id = R.string.hour)}"
            period.days < 30 -> "${period.days} ${stringResource(id = R.string.day)}"
            period.months < 12 -> "${period.months} ${stringResource(id = R.string.month)}"
            else -> "${period.years} ${stringResource(id = R.string.year)}"
        }

        return timeLeft + " ${stringResource(id = R.string.ago)}"
    }

    // Config Default
    @Composable
    fun createImageDefault(): ImageConfig {
        val image = painterResource(id = R.drawable.avt_young_girl)
        return remember {
            ImageComponentBuilder()
                .withConfig(newConfig = ImageConfig(
                    painter = image,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = AppColorTheme.secondary,
                            shape = CircleShape
                        )
                ))
                .build()
                .getConfig()
        }
    }

    fun createTextDefault(): TextConfig {
        return TextComponentBuilder()
            .withConfig(
                newConfig = TextConfig(
                    textStyle = AppTypography.bodyMedium,
                    color = AppColorTheme.onPrimary
                )
            )
            .build()
            .getConfig()
    }

    fun createButtonDefault(): ButtonConfig {
        return ButtonComponentBuilder()
            .withConfig(
                newConfig = ButtonConfig(
                    textStyle = AppTypography.bodyMedium,
                    colors = ButtonColors(
                        containerColor = AppColorTheme.primary,
                        contentColor = AppColorTheme.onPrimary,
                        disabledContainerColor = AppColorTheme.surface,
                        disabledContentColor = AppColorTheme.onSurface
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
            expanded = postState.expended,
            onDismissRequest = { if (postState.expended) postState.toggleExpendState() },
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
                                style = AppTypography.bodyMedium,
                                color = AppColorTheme.onPrimary
                            )
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = item.first),
                                contentDescription = null,
                                tint = AppColorTheme.onPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )

                    if (index < listItemMenu.lastIndex)
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 1.dp,
                            color = AppColorTheme.surface
                        )
                }
            }
        }
    }

    // endregion

    // region --- Fields ---

    private val postState: DropDownMenuState = DropDownMenuState()

    private val listItemMenu = listOf(
        Pair(R.drawable.ic_bookmark, R.string.bookmark),
        Pair(R.drawable.ic_analysis, R.string.analysis),
        Pair(R.drawable.ic_report, R.string.report)
    )

    private val textConfigDefault: TextConfig by lazy { createTextDefault() }
    private val buttonConfigDefault: ButtonConfig by lazy { createButtonDefault() }

    // endregion

}