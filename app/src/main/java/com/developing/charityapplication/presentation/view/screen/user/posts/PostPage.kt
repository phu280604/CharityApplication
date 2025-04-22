package com.developing.charityapplication.presentation.view.screen.user.posts

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.infrastructure.utils.ConverterData
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
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
import kotlin.collections.forEach

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
    state: List<LocalDateTime?>,
    isEdit: Boolean = false,
    onReset: () -> Unit,
    onChangeValue: (Boolean, LocalDateTime) -> Unit,
    modifier: Modifier
){
    LaunchedEffect(state[0]) {
        if(!isEdit) onReset()
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
                        val newValue = ConverterData.convertCalendarToLocalDateTime(value)
                        Log.d("Selected_datetime", "NewValue: ${newValue}")
                        onChangeValue(true, newValue)
                    },
                    isEnabled = if(!isEdit) true else false
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
                        val newValue = ConverterData.convertCalendarToLocalDateTime(value)
                        onChangeValue(false, newValue)
                    },
                    hasStart = if(state[0] != null) state[0].let {
                        val calendar = ConverterData.convertLocalDateToCalendar(it!!)
                        calendar
                    }
                    else null,
                    isEnabled = if(!isEdit) isEnabled else false
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
                            Log.d("EditContent", "URI: ${uri}")
                            Image(
                                painter = rememberAsyncImagePainter(uri),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            // endregion

                            // region - Pop Image/Video Button -
                            IconButton(
                                onClick = {
                                    onRemoveValue(uri)
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
        val requestFile = compressedFile.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("files", compressedFile.name, requestFile)
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
            onClick = { onCreatePost() },
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