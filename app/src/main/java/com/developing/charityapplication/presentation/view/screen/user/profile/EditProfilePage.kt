package com.developing.charityapplication.presentation.view.screen.user.profile

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.component.text.TextConfig
import com.developing.charityapplication.presentation.view.component.text.builder.TextComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
import com.yalantis.ucrop.UCrop
import java.io.File

// region --- Methods ---

@Composable
fun EditProfileScreen(navController: NavHostController) {
    // region -- Selected Image Value --
    val context = LocalContext.current
    val mediaUri = remember { mutableStateOf<Uri?>(null) }
    val cacheDir = context.cacheDir
    val pixelSize = with(LocalDensity.current) { 192.dp.roundToPx() }

    val cropLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val resultUri = UCrop.getOutput(result.data!!)
            resultUri?.let {
                mediaUri.value = it
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColorTheme.primary)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        // Change avatar
        EditAvatar(
            background = R.drawable.avt_young_girl,
            mediaUri = mediaUri,
            onClickChange = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier.weight(1f)
        )

        // Change name
        EditName(
            firstName = "Hà",
            lastName = "Thu",
            modifier = Modifier.weight(1f)
        )

        // Save change
        EditButton(
            onSaveChange = {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("selectedIndex", 0)

                navController.popBackStack()
                /* TODO: Save profile here */
            },
            modifier = Modifier.weight(0.2f)
        )
    }
}

// region --- Body Sections ---
@Composable
fun EditAvatar(
    background: Int,
    mediaUri: MutableState<Uri?>,
    onClickChange: () -> Unit,
    modifier: Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ){
        // region -- Show Avatar --
        Image(
            painter = if(mediaUri.value == null) painterResource(id = background) else rememberAsyncImagePainter(mediaUri.value),
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
fun EditName(
    firstName: String,
    lastName: String,
    modifier: Modifier
){
    // region - Component Config -
    val textConfig = createTextDefault()
    val textFieldConfig = createTextFieldDefault()
    // endregion

    val textState = remember { mutableStateOf(listOf(firstName, lastName)) }

    // region - List of Label -
    val labelNameValues = listOf(
        R.string.last_name,
        R.string.first_name
    )
    // endregion

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // region - Name Section -
        labelNameValues.forEachIndexed { index, item ->
            InputFieldComponentBuilder()
                .withConfig(
                    textFieldConfig.copy(
                        value = textState.value.get(index),
                        onValueChange = {
                            val textChange = it
                            textState.value = textState.value.toMutableStateList().apply {
                                this[index] = textChange
                            }
                        },
                        label = {
                            TextComponentBuilder()
                                .withConfig(
                                    textConfig.copy(
                                        text = buildAnnotatedString {
                                            withStyle(style = SpanStyle(color = Color.Black)) {
                                                append(stringResource(id = item))
                                            }
                                            withStyle(style = SpanStyle(color = Color.Red)) {
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
                            .fillMaxWidth()
                    )
                )
                .build()
                .BaseDecorate { }
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