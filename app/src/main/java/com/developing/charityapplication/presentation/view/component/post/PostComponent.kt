package com.developing.charityapplication.presentation.view.component.post

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.infrastructure.utils.ParseToAreaTimeZone
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.component.image.ImageConfig
import com.developing.charityapplication.presentation.view.component.image.builder.ImageComponentBuilder
import com.developing.charityapplication.presentation.view.component.post.decorator.IPostComponentDecorator
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.componentViewModel.postState.DropDownMenuState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.Period


@OptIn(ExperimentalMaterial3Api::class)
class PostComponent(
    private val config: PostConfig
) : IPostComponentDecorator {

    // region --- Overrides ---
    @Composable
    override fun BaseDecorate(content: @Composable (() -> Unit)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = AppColorTheme.primary)
        ) {
            // Post header
            PostHeader(modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
            )

            // Post content
            PostBody(
                onClick = { image -> config.maximizeImage(image) },
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            )

            // Post actions
            PostFooter(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp)
                    .fillMaxWidth()
                    .height(32.dp)
            )

            HorizontalDivider(
                thickness = 1.dp,
                color = AppColorTheme.surface
            )
        }
    }

    override fun getConfig(): PostConfig = config
    
    // endregion

    // region --- Methods ---

    // Header Section
    @Composable
    fun PostHeader(modifier: Modifier){
        var showBottomSheet by remember { mutableStateOf(false) }
        Row(
            modifier = modifier,
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
                            onClick = { showBottomSheet = true },
                            modifier = Modifier
                                .size(32.dp),
                        )
                    )
                    .build()
                    .BaseDecorate {  }
            }
            // endregion
        }
        OptionMenuLayout(
            isShow = showBottomSheet,
            onDismissed = { dismissed -> showBottomSheet = dismissed }
        )
    }

    // Body Section
    @Composable
    fun PostBody(
        onClick: (String) -> Unit,
        modifier: Modifier
    ){
        val maxLines = 3
        var expanded by remember { mutableStateOf(false) }
        val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
        val textLayoutResult = textLayoutResultState.value
        var finalText by remember { mutableStateOf(config.content) }
        val showLess = stringResource(id = R.string.minimize)
        val showMore = stringResource(id = R.string.maximize)
        var isClickable by remember { mutableStateOf(false) }

        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // region - Post text -
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
            ){
                LaunchedEffect(textLayoutResult) {
                    if(textLayoutResult == null) return@LaunchedEffect
                    when {
                        expanded -> {
                            finalText = config.content
                        }
                        !expanded && textLayoutResult.hasVisualOverflow -> {
                            val lastCharIndex = textLayoutResult.getLineEnd(maxLines - 1)
                            val adjustedText = config.content
                                .substring(startIndex = 0, endIndex = lastCharIndex)
                                .dropLast(showMore.length)
                                .dropLastWhile {
                                    it == '.'
                                }
                            finalText = "$adjustedText..."
                            isClickable = true
                        }
                    }
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = AppColorTheme.onPrimary,
                                fontSize = AppTypography.bodyMedium.fontSize,
                                fontWeight = AppTypography.bodyMedium.fontWeight,
                                fontFamily = AppTypography.bodyMedium.fontFamily
                            )
                        ) {
                            append(finalText)
                        }
                        if (isClickable)
                            withStyle(
                                style = SpanStyle(
                                    color = AppColorTheme.surface,
                                    fontSize = AppTypography.bodyMedium.fontSize,
                                    fontWeight = AppTypography.bodyMedium.fontWeight,
                                    fontFamily = AppTypography.bodyMedium.fontFamily
                                )
                            ) {
                                if(!expanded) append(" $showMore") else append("\n$showLess")
                            }
                    },
                    maxLines = if (expanded) Int.MAX_VALUE else maxLines,
                    overflow = TextOverflow.Visible,
                    onTextLayout = {
                        textLayoutResultState.value = it
                    },
                    modifier = Modifier
                        .clickable(enabled = isClickable){
                            expanded = !expanded
                        }
                        .animateContentSize()
                )
            }
            // endregion

            // region - Donation amount -
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .wrapContentWidth()
                    .height(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(56.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_donation),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(32.dp)
                    )
                    Text(
                        text = config.donationValue + " VNĐ",
                        style = AppTypography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
            // endregion

            // region - Date range -
            if(!config.dateRange.isEmpty())
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
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
                        modifier = Modifier.size(16.dp)
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
            if(!config.fileIds.isEmpty())
            {
                val isAtStart by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                    }
                }
                val isAtEnd by remember {
                    derivedStateOf {
                        val visibleItems = listState.layoutInfo.visibleItemsInfo
                        val totalItems = listState.layoutInfo.totalItemsCount
                        val lastVisibleItem = visibleItems.lastOrNull()?.index ?: 0
                        lastVisibleItem >= totalItems - 1
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(384.dp)
                ) {
                    LazyRow(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(0f)
                            .background(color = AppColorTheme.primary),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(config.fileIds) { index, item ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(384.dp)
                                    .clickable { onClick(item) },
                                contentAlignment = Alignment.TopEnd
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(item),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                    // Show Left Arrow
                    if (!isAtStart && config.fileIds.count() >= 2) {
                        IconButton(
                            onClick = {
                                // scroll left
                                coroutineScope.launch {
                                    listState.animateScrollToItem(maxOf(listState.firstVisibleItemIndex - 1, 0))
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = AppColorTheme.onSurface.copy(alpha = 0.8f),
                                contentColor = AppColorTheme.onPrimary
                            ),
                            modifier =  Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 8.dp)
                                .size(40.dp)
                                .zIndex(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Arrow Left",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    // Show Right Arrow
                    if (!isAtEnd && config.fileIds.count() >= 2) {
                        IconButton(
                            onClick = {
                                // scroll right
                                coroutineScope.launch {
                                    listState.animateScrollToItem(listState.firstVisibleItemIndex + 1)
                                }
                            },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = AppColorTheme.onSurface.copy(alpha = 0.8f),
                                contentColor = AppColorTheme.onPrimary
                            ),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 8.dp)
                                .size(40.dp)
                                .zIndex(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Arrow Right",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
            // endregion
        }
    }

    // Footer Section
    @Composable
    fun PostFooter(modifier: Modifier){
        var listFooter = listOf(
            Pair(R.drawable.ic_love, R.string.like_post),
            Pair(R.drawable.ic_comment, R.string.comment_post),
            Pair(R.drawable.ic_share, R.string.share_post),
            Pair(R.drawable.ic_donation, R.string.donate_post)
        )
        LazyRow(
            modifier = modifier,
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
        val parseCreateAt = ParseToAreaTimeZone.parseToVietnamTime(createAt.toString())
        val duration = Duration.between(parseCreateAt, currentDateTime)
        val period = Period.between(parseCreateAt.toLocalDate(), currentDateTime.toLocalDate())

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
    fun OptionMenuLayout(
        isShow: Boolean,
        onDismissed: (Boolean) -> Unit
    ){

        val sheetState = rememberModalBottomSheetState()

        val listItemMenu = if(config.readOnly){
            listOf(
                Pair(R.drawable.ic_bookmark, R.string.bookmark_post),
                Pair(R.drawable.ic_analytics, R.string.analysis_post),
                Pair(R.drawable.ic_report, R.string.report_post)
            )
        }
        else{
            listOf(
                Pair(R.drawable.ic_bookmark, R.string.bookmark_post),
                Pair(R.drawable.ic_edit, R.string.edit_post),
                Pair(R.drawable.ic_analytics, R.string.analysis_post),
                Pair(R.drawable.ic_report, R.string.report_post),
                Pair(R.drawable.ic_delete, R.string.delete_post)
            )
        }

        if(isShow)
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { onDismissed(false) },
                containerColor = AppColorTheme.primary
            ) {
                listItemMenu.forEachIndexed { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clickable(
                                role = Role.Button,
                                onClick = {
                                    when(item.second){
                                        R.string.edit_post -> {
                                            config.onEdit()
                                            onDismissed(false)
                                        }
                                        R.string.delete_post -> {
                                            config.onDelete()
                                            onDismissed(false)
                                        }
                                        else -> { onDismissed(false) }
                                    }
                                    /*TODO: Implement drop down menu logic*/
                                }
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
                    ) {
                        Image(
                            painter = painterResource(id = item.first),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = AppColorTheme.onPrimary),
                            modifier = Modifier
                                .size(24.dp)
                        )

                        Text(
                            text = stringResource(id = item.second),
                            style = AppTypography.bodyMedium,
                            color = AppColorTheme.onPrimary
                        )
                    }


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

    // endregion

    // region --- Fields ---

    private val postState: DropDownMenuState = DropDownMenuState()

    private val textConfigDefault: TextConfig by lazy { createTextDefault() }
    private val buttonConfigDefault: ButtonConfig by lazy { createButtonDefault() }

    // endregion

}