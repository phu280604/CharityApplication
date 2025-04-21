@file:OptIn(ExperimentalAnimationApi::class)

package com.developing.charityapplication.presentation.view.screen.user.profile

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.infrastructure.utils.DefaultValue
import com.developing.charityapplication.infrastructure.utils.DownloadImage
import com.developing.charityapplication.infrastructure.utils.Provinces
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.screenEvent.EditProfileEvent
import com.developing.charityapplication.presentation.state.screenState.EditProfileState
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.profile.EditProfileViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.FooterViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.HeaderViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.profileViewModel.ProfileViewModel
import com.yalantis.ucrop.UCrop
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream


// region --- Methods ---

@Composable
fun EditProfileScreen(
    navController: NavHostController,
    editProfileVM: EditProfileViewModel
) {
    // region -- value Default --
    val overLoadAvaSms = stringResource(id = R.string.overLoad_size_avatar)
    val changeSuccessful = stringResource(id = R.string.change_successful)

    val context = LocalContext.current
    // endregion

    // region -- ViewModel --
    val profileVM: ProfileViewModel = hiltViewModel()
    // endregion

    // region -- State --
    val state by editProfileVM.state.collectAsState()
    val profile by profileVM.profileResponse.collectAsState()

    var mediaUri by remember { mutableStateOf<Uri?>(null) }
    var avatarPart by remember { mutableStateOf<MultipartBody.Part?>(null) }

    var isShow by remember {mutableStateOf(false)}
    // endregion

    // region -- Set State Data --
    LaunchedEffect(Unit) {
        editProfileVM.onEvent(EditProfileEvent.LastNameChange(editProfileVM.profileInfo?.lastName ?: ""))
        editProfileVM.onEvent(EditProfileEvent.FirstNameChange(editProfileVM.profileInfo?.firstName ?: ""))
        editProfileVM.onEvent(EditProfileEvent.UsernameChange(editProfileVM.profileInfo?.username ?: ""))
        editProfileVM.onEvent(EditProfileEvent.LocationChange(editProfileVM.profileInfo?.location ?: ""))
        if(!editProfileVM.avatar.isEmpty()){
            val avatar = DownloadImage.prepareImageParts(context, editProfileVM.avatar, "avatar")
            editProfileVM.onEvent(EditProfileEvent.AvatarChange(avatar))
            Log.d("ProfileAvt", "currentAvt: $avatar")
        }
        isShow = true
    }
    // endregion

    // region -- Selected Image Value --
    val cacheDir = context.cacheDir
    val pixelSize = with(LocalDensity.current) { 192.dp.roundToPx() }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let { data ->
                val resultUri = UCrop.getOutput(data)
                resultUri?.let { uri ->
                    val file = File(uri.path!!)

                    val fileSizeInMB = file.length() / (1024 * 1024)
                    if (fileSizeInMB >= 1) {
                        Toast.makeText(context, overLoadAvaSms, Toast.LENGTH_SHORT).show()
                        return@let
                    }

                    val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                    val compressedFile = File(cacheDir, "compressed_${System.currentTimeMillis()}.jpg")
                    val outputStream = FileOutputStream(compressedFile)

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
                    outputStream.flush()
                    outputStream.close()

                    val compressedSizeMB = compressedFile.length() / (1024 * 1024)
                    Log.d("AvatarUpload", "Ảnh nén: ${compressedSizeMB}MB")

                    val requestFile = compressedFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    avatarPart = MultipartBody.Part.createFormData("avatar", compressedFile.name, requestFile)
                    editProfileVM.onEvent(EditProfileEvent.AvatarChange(avatarPart))
                    Log.d("AvatarUpload", "fileName: ${avatarPart?.body}")

                    mediaUri = Uri.fromFile(compressedFile)
                    Log.d("AvatarUpload", "fileName: ${mediaUri}")
                }

            }
        }
    }
    // endregion

    // region -- Crop Image --
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val destinationFile = File(cacheDir, "cropped_${System.currentTimeMillis()}.jpg")
            val destinationUri = Uri.fromFile(destinationFile)

            val intent = UCrop.of(uri, destinationUri)
                .withAspectRatio(1f, 1f)
                .withMaxResultSize(pixelSize, pixelSize)
                .getIntent(context)

            cropLauncher.launch(intent)
        }
    }
    // endregion

    // region -- Call API --
    Log.d("EditContent", "MediaPart: ${state.avatar.toString()}")
    LaunchedEffect(true) {
        editProfileVM.validationEvents.collect { event ->
            when(event){
                is EditProfileViewModel.ValidationEvent.Success -> {
                    val newProfileInfo = RequestUpdateProfileM(
                        lastName = state.lastName,
                        firstName = state.firstName,
                        username = state.username,
                        location = state.location
                    )
                    val newAvatar = if (!state.avatar.toString().isEmpty()) state.avatar
                    else {
                        DownloadImage.prepareImageParts(context, editProfileVM.avatar, "avatar")
                    }
                    profileVM.updateProfile(
                        profileId = editProfileVM.profileId,
                        profileInfo = newProfileInfo,
                        avatar = newAvatar
                    )
                }
            }
        }
    }
    // endregion

    // region -- Navigate Back To Profile --
    LaunchedEffect(profile) {
        if(profile?.code == StatusCode.SUCCESS.code && profile?.result != null){
            Toast.makeText(
                context,
                changeSuccessful,
                Toast.LENGTH_LONG
            ).show()

            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set("profileSelectedIndex", 0)
            Log.d("EditProfile", "hello")
            navController.popBackStack()
        }

        if (profile?.code != StatusCode.SUCCESS.code && profile != null){
            val sms = StatusCode.fromStatusResId(profile?.code ?: 0)
            Toast.makeText(
                context,
                sms,
                Toast.LENGTH_LONG
            ).show()
        }

        profileVM.resetProfileResponse()
    }
    // endregion

    if(isShow){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppColorTheme.primary)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region - Change avatar -
            Log.d("EditContent", "Background: ${editProfileVM.avatar}")
            EditAvatar(
                background = if(editProfileVM.avatar.isEmpty())
                    painterResource(DefaultValue.avatar)
                else
                    rememberAsyncImagePainter(editProfileVM.avatar),
                mediaUri = mediaUri,
                onClickChange = {
                    imagePickerLauncher.launch("image/*")
                    editProfileVM.onEvent(EditProfileEvent.AvatarChange(avatarPart))
                },
                modifier = Modifier.weight(0.8f)
            )
            // endregion

            // region - Change name -
            EditInfo(
                state = state,
                onValueChange = {
                        index, newValue ->
                    when(index){
                        0 -> editProfileVM.onEvent(EditProfileEvent.LastNameChange(newValue))
                        1 -> editProfileVM.onEvent(EditProfileEvent.FirstNameChange(newValue))
                        2 -> editProfileVM.onEvent(EditProfileEvent.LocationChange(newValue))
                    }
                },
                modifier = Modifier.weight(1f)
            )
            // endregion

            // region - Save change -
            EditButton(
                onSaveChange = {
                    editProfileVM.onEvent(EditProfileEvent.Submit)
                    /* TODO: Save profile here */
                },
                modifier = Modifier.weight(0.2f)
            )
            // endregion
        }
    }
}

// region --- Body Sections ---
@Composable
fun EditAvatar(
    background: Painter,
    mediaUri: Uri?,
    onClickChange: () -> Unit,
    modifier: Modifier
){

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        // region -- Show Avatar --
        Image(
            painter = if(mediaUri == null) background else rememberAsyncImagePainter(model = mediaUri),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(192.dp)
                .clip(CircleShape)
                .border(
                    width = 0.5f.dp,
                    color = AppColorTheme.onPrimary,
                    shape = CircleShape
                )
        )
        // endregion

        // region -- Icon Button Change Avatar --
        IconButton(
            onClick = {
                onClickChange()
                /*TODO: Implement change background*/
            },
            colors = IconButtonDefaults.iconButtonColors().copy(
                containerColor = AppColorTheme.onSurface.copy(alpha = 0.8f),
                contentColor = AppColorTheme.onPrimary
            ),
            modifier = Modifier
                .size(48.dp)
                .offset(x = 72.dp, y = 72.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_image_rectangle),
                contentDescription = null,
                modifier = Modifier
                    .size(32.dp)
            )
        }
        // endregion
    }
}

@Composable
fun EditInfo(
    state: EditProfileState,
    onValueChange: (Int, String) -> Unit,
    modifier: Modifier
){
    // region - Component Config -
    val textConfig = createTextDefault()
    val textFieldConfig = createTextFieldDefault()

    val provinces: List<String> = Provinces().provincesOfVietnam

    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
    // endregion

    // region -- State --
    var expanded by remember { mutableStateOf(false) }
    // endregion

    // region - List of Label -
    val labelNameValues = listOf(
        R.string.last_name,
        R.string.first_name
    )
    // endregion

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // region - Name Section -
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            labelNameValues.forEachIndexed { index, item ->
                InputFieldComponentBuilder()
                    .withConfig(
                        textFieldConfig.copy(
                            value = when(index){
                                0 -> state.lastName
                                else -> state.firstName
                            },
                            onValueChange = {
                                onValueChange(index, it)
                            },
                            label = {
                                TextComponentBuilder()
                                    .withConfig(
                                        textConfig.copy(
                                            text = buildAnnotatedString {
                                                withStyle(style = SpanStyle(
                                                    color = AppColorTheme.onPrimary,
                                                    fontFamily = AppTypography.bodyMedium.fontFamily,
                                                    fontWeight = AppTypography.bodyMedium.fontWeight,
                                                    fontSize = AppTypography.bodyMedium.fontSize,
                                                )) {
                                                    append(stringResource(id = item))
                                                }
                                                withStyle(style = SpanStyle(
                                                    color = AppColorTheme.onError,
                                                    fontFamily = AppTypography.bodyMedium.fontFamily,
                                                    fontWeight = AppTypography.bodyMedium.fontWeight,
                                                    fontSize = AppTypography.bodyMedium.fontSize,
                                                )) {
                                                    append(" *")
                                                }
                                            }.toString(),
                                            maxLine = 1
                                        )
                                    )
                                    .build()
                                    .BaseDecorate { }
                            },
                            //isError = isError,
                            /*supportText = {
                                if (isError) {
                                    TextComponentBuilder()
                                        .withConfig(
                                            textConfig.copy(
                                                text = "${stringResource(id = item)} ${errorText}",
                                                textStyle = AppTypography.labelMedium.copy(
                                                    fontWeight = FontWeight.Light
                                                ),
                                                color = AppColorTheme.onError
                                            )
                                        )
                                        .build()
                                        .BaseDecorate { }
                                }
                            },*/
                            modifier = Modifier
                                .weight(1f)
                        )
                    )
                    .build()
                    .BaseDecorate { }
            }
        }
        // endregion

        // region -- Location Section --
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.TopCenter
        ){
            InputFieldComponentBuilder()
                .withConfig(
                    InputFieldConfig(
                        value = state.location,
                        onValueChange = {},
                        label = {
                            Text(
                                text = stringResource(id = R.string.selected_location),
                                fontSize = AppTypography.bodyMedium.fontSize,
                                style = AppTypography.bodyMedium,
                                maxLines = 1
                            )
                        },
                        shape = RoundedCornerShape(4.dp),
                        leadingIcon = {
                            IconButton(
                                onClick = { expanded = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = AppColorTheme.onPrimary,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size
                            }
                    )
                )
                .build()
                .BaseDecorate {  }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                        .height(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = AppColorTheme.primary, shape = RoundedCornerShape(8.dp))
                ) {
                    provinces.forEach { province ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = province,
                                    style = AppTypography.bodyMedium,
                                    color = AppColorTheme.onPrimary
                                )
                            },
                            onClick = {
                                onValueChange(2, province)
                                expanded = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        )
                        if (provinces.last() != province)
                            HorizontalDivider(
                                thickness = 0.5f.dp,
                                color = AppColorTheme.surface
                            )
                    }
                }
        }
        // endregion
    }
}

@Composable
fun EditButton(
    onSaveChange: () -> Unit,
    modifier: Modifier
){
    Box(
        modifier = modifier,
    ){
        // region -- Button Save Change --
        Button(
            onClick = onSaveChange,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = AppColorTheme.secondary,
                contentColor = AppColorTheme.primary
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.save_change),
                style = AppTypography.bodyMedium
            )
        }
        // endregion
    }
}

// region --- Creating Components --
@Composable
fun createTextDefault() : TextConfig {
    return remember {
        TextComponentBuilder()
            .withConfig(
                TextConfig(
                    color = AppColorTheme.onPrimary,
                    textStyle = AppTypography.titleMedium
                )
            )
            .build()
            .getConfig()
    }
}

@Composable
fun createTextFieldDefault(
    modifier: Modifier = Modifier
) : InputFieldConfig{
    return remember {
        InputFieldConfig(
            supportText = { /*TODO: Implement supportText*/ },
            maxLine = 1,
            shape = RoundedCornerShape(4.dp),
            modifier = modifier
                .fillMaxWidth()
        )
    }
}
// endregion
// endregion

// endregion