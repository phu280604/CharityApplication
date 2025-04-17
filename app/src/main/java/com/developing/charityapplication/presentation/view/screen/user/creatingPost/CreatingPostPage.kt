@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.creatingPost

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalDensity
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.infrastructure.utils.ConverterData
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.screenEvent.CreatingPostEvent
import com.developing.charityapplication.presentation.event.screenEvent.EditProfileEvent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.creatingPost.CreatingPostViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.postViewModel.PostViewModel
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
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
                    text = stringResource(id = R.string.post_page),
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
                    painter = painterResource(id = R.drawable.post),
                    contentDescription = null,
                    tint = AppColorTheme.secondary,
                    modifier = Modifier.size(32.dp)
                )
            }
        )
    }
}

@Composable
fun CreatingPostPageScreen(navController: NavHostController){
    // region -- Value Default --
    val context = LocalContext.current

    val creatingSuccessful = stringResource(id = R.string.creating_successful)
    // endregion

    // region -- ViewModel --
    val creatingPostVM: CreatingPostViewModel = hiltViewModel()
    val postVM: PostViewModel = hiltViewModel()
    // endregion

    // region -- State --
    val state by creatingPostVM.state.collectAsState()
    val postResponse by postVM.postResponse.collectAsState()
    val profileId = DataStoreManager.getProfileId(context).collectAsState(initial = null)
    val multipartList = remember { mutableStateListOf<Pair<Uri, MultipartBody.Part>>() }
    Log.d("profileId", "CreatingPost: ${profileId}")
    // endregion

    // region -- Call Api --
    LaunchedEffect(true) {
        creatingPostVM.validationEvents.collect{
            event ->
            when(event){
                is CreatingPostViewModel.ValidationEvent.Success -> {
                    /*TODO: Implement get data*/

                }
            }
        }
    }
    // endregion

    // region -- Back To HomePage --
    LaunchedEffect(postResponse) {
        if(postResponse?.code == 1000 && postResponse?.result != null){
            Toast.makeText(
                context,
                creatingSuccessful,
                Toast.LENGTH_LONG
            ).show()

            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("selectedIndex", 0)

            navController.popBackStack()
        }

        if (postResponse?.code != 1000){
            val sms = StatusCode.fromStatusResId(postResponse?.code ?: 0)
            Toast.makeText(
                context,
                sms,
                Toast.LENGTH_LONG
            ).show()
        }

        /*TODO: Reset postResponse*/
        //profileVM.resetProfileResponse()
    }
    // endregion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColorTheme.primary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContentBox(
            state = state.content,
            onChangeValue = {
                newValue ->
                creatingPostVM.onEvent(CreatingPostEvent.ContentChange(newValue))
            },
            modifier = Modifier.weight(0.5f),
        )

        DTPBox(
            state = listOf(state.startDate, state.endDate),
            onReset = {
                creatingPostVM.onEvent(CreatingPostEvent.ResetEndDateChange())
            },
            onChangeValue = {
                isStart, newValue ->
                when(isStart){
                    true -> creatingPostVM.onEvent(CreatingPostEvent.StartDateChange(newValue))
                    false -> creatingPostVM.onEvent(CreatingPostEvent.EndDateChange(newValue))
                }
            },
            modifier = Modifier.weight(0.3f)
        )

        ImageBox(
            state = multipartList.map { it.first },
            onChangeValue = {
                uri, image ->
                multipartList.add(Pair(uri, image))
                Log.d("ImagesSave", "Multi: ${multipartList.size}")
            },
            onRemoveValue = {
                uri ->
                val targetUriString = uri.toString()
                multipartList.removeIf{ it.first.toString() == targetUriString }
                Log.d("ImagesSave", "Multi: ${multipartList.size}")
            },
            modifier = Modifier.weight(1f)
        )

        ButtonBox(
            modifier = Modifier.weight(0.2f),
            onCreatePost = { /*TODO: Implement creating post*/ }
        )
    }
}

// region -- Body Section --
@Composable
fun ContentBox(
    state: String,
    onChangeValue: (String) -> Unit,
    modifier: Modifier
){
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
                    value = state,
                    onValueChange = { onChangeValue(it) },
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
fun DTPBox(
    state: List<LocalDate?>,
    onReset: () -> Unit,
    onChangeValue: (Boolean, LocalDate) -> Unit,
    modifier: Modifier
){
    LaunchedEffect(state[0]) {
        onReset()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ){
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = AppTypography.titleMedium.fontFamily,
                        fontSize = AppTypography.titleMedium.fontSize,
                        fontWeight = AppTypography.titleMedium.fontWeight,
                        color = AppColorTheme.secondary
                    )
                ) {
                    append("${stringResource(id = R.string.timeline_title)} ")
                }
                withStyle(
                    style = SpanStyle(
                        fontFamily = AppTypography.titleMedium.fontFamily,
                        fontSize = AppTypography.titleMedium.fontSize,
                        fontWeight = AppTypography.titleMedium.fontWeight,
                        color = AppColorTheme.onError
                    )
                ) {
                    append("*")
                }
            }
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
                            val datetimeString = if (state[0] != null) state[0].let {
                                val calendar = ConverterData.convertLocalDateToCalendar(it!!)
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val formatted = sdf.format(calendar.time)
                                Log.d("Selected_datetime", "show: $formatted")
                                formatted
                            }
                            else
                                stringResource(id = R.string.timeline_date)
                            append(datetimeString)
                        }
                    },
                    onCalendarChange = {
                        value ->
                        Log.d("Selected_datetime", "Value: ${value}")
                        val newValue = ConverterData.convertCalendarToLocalDate(value)
                        Log.d("Selected_datetime", "NewValue: ${newValue}")
                        onChangeValue(true, newValue)
                    }
                )

                // endregion

                // region - DTP End Date -
                var isEnabled = false
                if (state[0] != null) {
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
                            val datetimeString = if (state[1] != null) state[1].let {
                                val calendar = ConverterData.convertLocalDateToCalendar(it!!)
                                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                val formatted = sdf.format(calendar.time)
                                Log.d("Selected_datetime", "show: $formatted")
                                formatted
                            }
                            else
                                stringResource(id = R.string.timeline_date)
                            append(datetimeString)
                        }
                    },
                    onCalendarChange = {
                        value ->
                        val newValue = ConverterData.convertCalendarToLocalDate(value)
                        onChangeValue(false, newValue)
                    },
                    hasStart = if(state[0] != null) state[0].let {
                        val calendar = ConverterData.convertLocalDateToCalendar(it!!)
                        calendar
                    }
                    else null,
                    isEnabled = isEnabled
                )
                // endregion
            }
        }
    }
}

@Composable
fun ImageBox(
    state: List<Uri>,
    onChangeValue: (Uri, MultipartBody.Part) -> Unit,
    onRemoveValue: (Uri) -> Unit,
    modifier: Modifier
){
    val scrollState = rememberScrollState()

    val context = LocalContext.current
    val cacheDir = context.cacheDir

    // Danh sách Uri (hiển thị trong UI)

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        uris.forEach { uri ->
            val multipart = processImageUri(context, uri, cacheDir)
            multipart?.let { onChangeValue(uri, it) }
        }

    }

    /*val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        val contentResolver = context.contentResolver
        val filteredUris = uris.filter { uri ->
            val type = contentResolver.getType(uri) ?: ""
            // Comment Get Video
            *//*(type.startsWith("image/") || type.startsWith("video/")) &&
                    mediaUris.none { it.toString() == uri.toString() }*//*
            type.startsWith("image/") &&
                    mediaUris.none { it.toString() == uri.toString() }
        }
        mediaUris.addAll(filteredUris)

    }
*/
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
                if (state.isEmpty()){
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

                            /*TODO: Open comment when add more video*/
                            //launcher.launch("*/*")
                            imagePickerLauncher.launch("image/*")
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
            if (state.isNotEmpty()){
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .horizontalScroll(scrollState),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
                ) {
                    state.forEach { uri ->
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

                            // Comment Show Video
                            /*if(Checker.isVideo(uri, context)){
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
                            }*/
                            // endregion

                            // region - Pop Image/Video Button -
                            IconButton(
                                onClick = {
                                    onRemoveValue(uri)
                                    /*val targetUriString = uri.toString()
                                    imageUris.removeIf { it.toString() == targetUriString }
                                    val multipart = processImageUri(context, uri, cacheDir)
                                    multipart?.let { onRemoveValue(it) }*/
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
                            /*TODO: Open comment when add more video*/
                            //launcher.launch("*/*")
                            imagePickerLauncher.launch("image/*")
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

fun processImageUri(context: Context, uri: Uri, cacheDir: File): MultipartBody.Part? {
    try {
        val file = File(uri.path ?: return null)

        // Kiểm tra dung lượng
        val fileSizeInMB = file.length() / (1024 * 1024)
        if (fileSizeInMB >= 1) {
            Toast.makeText(context, "Ảnh vượt quá 1MB", Toast.LENGTH_SHORT).show()
            return null
        }

        // Chuyển Uri thành Bitmap
        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        val compressedFile = File(cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(compressedFile)

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        outputStream.flush()
        outputStream.close()

        // Tạo Multipart
        val requestFile = compressedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("image", compressedFile.name, requestFile)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
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
    onCalendarChange: (Calendar) -> Unit,
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
                            onCalendarChange(picked)
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