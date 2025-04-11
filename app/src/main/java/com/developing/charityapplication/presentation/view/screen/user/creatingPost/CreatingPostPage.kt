@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.creatingPost

import android.app.DatePickerDialog
import android.net.Uri
import android.view.ViewGroup
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// region --- Methods ---

@Composable
fun HeaderCreatingPost(navController: NavHostController){
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.creating_page),
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
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            }
        )
    }
}

@Composable
fun CreatingPostPageScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColorTheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContentBox(modifier = Modifier.weight(0.5f))

        DTPBox(modifier = Modifier.weight(0.3f))

        ImageBox(modifier = Modifier.weight(1f))

        ButtonBox(
            modifier = Modifier.weight(0.2f),
            onCreatePost = { /*TODO: Implement creating post*/ }
        )
    }
}

// region -- Body Section --
@Composable
fun ContentBox(modifier: Modifier){
    var contentVal by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = AppColorTheme.surface,
                shape = RoundedCornerShape(8.dp)
            )
    ){
        InputFieldComponentBuilder()
            .withConfig(
                InputFieldConfig(
                    value = contentVal,
                    onValueChange = { contentVal = it },
                    placeHolder = {
                        Text(
                            text = stringResource(R.string.content_area),
                            style = AppTypography.bodyMedium
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            )
            .build()
            .BasicDecorate {  }
    }
}

@Composable
fun DTPBox(modifier: Modifier){
    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }

    var selectedDate1 = remember { mutableStateOf<Calendar?>(null) }
    var selectedDate2 = remember { mutableStateOf<Calendar?>(null) }

    LaunchedEffect(selectedDate1.value) {
        selectedDate2.value = null
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Text(
            text = stringResource(id = R.string.timeline_title),
            style = AppTypography.titleMedium,
            color = AppColorTheme.secondary
        )
        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = AppColorTheme.surface,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ){
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val fontWeight = AppTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                // region - DTP Start Date -
                CreatingDTP(
                    title = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontFamily = AppTypography.bodyMedium.fontFamily,
                            fontSize = AppTypography.bodyMedium.fontSize,
                            fontWeight = fontWeight.fontWeight
                        )) {
                            append("${stringResource(id = R.string.timeline_from)}: ")
                        }
                        withStyle(style = SpanStyle(
                            fontFamily = AppTypography.bodyMedium.fontFamily,
                            fontSize = AppTypography.bodyMedium.fontSize,
                            fontWeight = AppTypography.bodyMedium.fontWeight
                        )) {
                            val datetimeString = if (selectedDate1.value != null) selectedDate1.let {
                                dateFormat.format(it.value!!.time)
                            }
                            else
                                stringResource(id = R.string.timeline_date)
                            append(datetimeString)
                        }
                    },
                    dateState = selectedDate1
                )

                // endregion

                // region - DTP End Date -
                var isEnabled = false
                if (selectedDate1.value != null) {
                    isEnabled = true
                }
                CreatingDTP(
                    title = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontFamily = AppTypography.bodyMedium.fontFamily,
                            fontSize = AppTypography.bodyMedium.fontSize,
                            fontWeight = fontWeight.fontWeight
                        )) {
                            append("${stringResource(id = R.string.timeline_to)}: ")
                        }
                        withStyle(style = SpanStyle(
                            fontFamily = AppTypography.bodyMedium.fontFamily,
                            fontSize = AppTypography.bodyMedium.fontSize,
                            fontWeight = AppTypography.bodyMedium.fontWeight
                        )) {
                            val datetimeString = if (selectedDate2.value != null) selectedDate2.let {
                                dateFormat.format(it.value!!.time)
                            }
                            else
                                stringResource(id = R.string.timeline_date)
                            append(datetimeString)
                        }
                    },
                    dateState = selectedDate2,
                    hasStart = selectedDate1.value,
                    isEnabled = isEnabled
                )
                // endregion
            }
        }
    }
}

@Composable
fun ImageBox(modifier: Modifier){
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val mediaUris = remember { mutableStateListOf<Uri>() }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        val contentResolver = context.contentResolver
        val filteredUris = uris.filter { uri ->
            val type = contentResolver.getType(uri) ?: ""
            (type.startsWith("image/") || type.startsWith("video/")) &&
                    mediaUris.none { it.toString() == uri.toString() }
        }
        mediaUris.addAll(filteredUris)

    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ){
        Text(
            text = stringResource(id = R.string.media_title),
            style = AppTypography.titleMedium,
            color = AppColorTheme.secondary
        )
        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = AppColorTheme.surface,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            // region - Title -
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if (mediaUris.isEmpty()){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_image_circle),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = AppColorTheme.onPrimary
                    )
                    Text(
                        text = stringResource(id = R.string.upload_media),
                        style = AppTypography.bodyMedium,
                        color = AppColorTheme.onPrimary
                    )
                    Text(
                        text = stringResource(id = R.string.media_condi),
                        style = AppTypography.labelMedium,
                        color = AppColorTheme.onPrimary
                    )
                    Button(
                        onClick = {
                            // Chọn cả ảnh và video
                            launcher.launch("*/*")
                        },
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = AppColorTheme.secondary,
                            contentColor = AppColorTheme.primary
                        ),
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.media_button),
                            style = AppTypography.bodyMedium
                        )
                    }

                }
            }
            // endregion

            // region - Item List -
            if (mediaUris.isNotEmpty()){
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .horizontalScroll(scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    mediaUris.forEach { uri ->
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
                                ),
                            contentAlignment = Alignment.TopEnd
                        ){
                            // region - Show Image/Video -
                            if (Checker.isImage(uri, context)) {
                                Image(
                                    painter = rememberAsyncImagePainter(uri),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }

                            if(Checker.isVideo(uri, context)){
                                AndroidView(
                                    factory = { ctx ->
                                        VideoView(ctx).apply {
                                            layoutParams = ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.MATCH_PARENT
                                            )
                                            setVideoURI(uri)
                                            setOnPreparedListener { it.isLooping = true; start() }
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(0.dp))
                                        .aspectRatio(1f)

                                )
                            }
                            // endregion

                            // region - Pop Image/Video Button -
                            IconButton(
                                onClick = {
                                    val targetUriString = uri.toString()
                                    mediaUris.removeIf { it.toString() == targetUriString }
                                },
                                colors = IconButtonDefaults.iconButtonColors().copy(
                                    containerColor = AppColorTheme.secondary.copy(alpha = 0.5f),
                                    contentColor = AppColorTheme.primary
                                ),
                                modifier = Modifier
                                    .size(24.dp)
                                    .offset(x = (-4).dp, y = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            // endregion
                        }
                    }

                    // region - Add More Image/Video -
                    IconButton(
                        onClick = {
                            launcher.launch("*/*")
                        },
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = AppColorTheme.primary,
                            contentColor = AppColorTheme.secondary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null
                        )
                    }
                    // endregion
                }
            }
            // endregion
        }
    }
}

@Composable
fun ButtonBox(
    modifier: Modifier,
    onCreatePost: () -> Unit
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = onCreatePost,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = AppColorTheme.secondary,
                contentColor = AppColorTheme.primary
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.creating_post),
                style = AppTypography.titleMedium
            )
        }
    }
}

// region -- DTP Components --
@Composable
fun CreatingDTP(
    title: AnnotatedString,
    dateState: MutableState<Calendar?>,
    hasStart: Calendar? = null,
    isEnabled: Boolean = true
) {
    val context = LocalContext.current
    var timelineDate = Calendar.getInstance()

    if (hasStart != null) {
        timelineDate = hasStart
    }

    Text(
        text = title,
        color = if (!isEnabled) AppColorTheme.onSurface else AppColorTheme.onPrimary,
        modifier = Modifier
            .clickable(
                enabled = isEnabled,
                role = Role.Button,
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            val picked = Calendar.getInstance().apply {
                                set(year, month, day)
                            }
                            dateState.value = picked
                        },
                        timelineDate.get(Calendar.YEAR),
                        timelineDate.get(Calendar.MONTH),
                        timelineDate.get(Calendar.DAY_OF_MONTH)
                    ).apply {
                        datePicker.minDate = timelineDate.timeInMillis
                    }.show()
                }
            )
    )
}
// endregion
// endregion

// endregion