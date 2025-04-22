@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.postModel.RequestPostContentM
import com.developing.charityapplication.infrastructure.utils.Checker
import com.developing.charityapplication.infrastructure.utils.ConverterData
import com.developing.charityapplication.infrastructure.utils.StatusCode
import com.developing.charityapplication.presentation.event.screenEvent.CreatingPostEvent
import com.developing.charityapplication.presentation.view.component.inputField.InputFieldConfig
import com.developing.charityapplication.presentation.view.component.inputField.builder.InputFieldComponentBuilder
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.creatingPost.CreatingPostViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.postViewModel.PostViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
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

    val title = stringResource(id = R.string.content_area)
    // endregion

    // region -- ViewModel --
    val creatingPostVM: CreatingPostViewModel = hiltViewModel()
    val postVM: PostViewModel = hiltViewModel()
    // endregion

    // region -- State --
    val state by creatingPostVM.state.collectAsState()
    val postResponse by postVM.postResponse.collectAsState()
    var isError by remember { mutableStateOf(false) }
    val profileId by DataStoreManager.getProfileId(context).collectAsState(initial = null)
    val multipartList = remember { mutableStateListOf<Pair<Uri, MultipartBody.Part>>() }

    // endregion

    // region -- Call Api --
    LaunchedEffect(true) {
        creatingPostVM.validationEvents.collect{ event ->
            when(event){
                is CreatingPostViewModel.ValidationEvent.Success -> {
                    val files = multipartList.map { it.second }
                    files.forEach {
                            item ->
                        Log.d("MediaItems", "CreatingPost: ${item}")
                    }
                    Log.d("DateTime", state.startDate.toString())
                    postVM.createPost(
                        postRequest = RequestPostContentM(
                            profileId = profileId ?: "",
                            content = state.content,
                            donationStartTime = state.startDate.toString(),
                            donationEndTime = state.endDate.toString()
                        ),
                        files = files
                    )
                }
            }
        }
    }
    // endregion

    LaunchedEffect(state) {
        var errorSms = ""
        if(state.contentError != null) {
            errorSms =  title + " " + state.contentError.toString()
            isError = true
        }

        if(state.startDateError != null) {
            errorSms = state.startDateError.toString()
            isError = true
        }

        if(state.endDateError != null) {
            errorSms = state.endDateError.toString()
            isError = true
        }

        if(isError)
        {
            Toast.makeText(
                context,
                errorSms,
                Toast.LENGTH_SHORT
            ).show()
            creatingPostVM.resetErrorState()
            isError = false
        }
    }

    // region -- Back To HomePage --
    LaunchedEffect(postResponse) {
        if(postResponse?.code == StatusCode.SUCCESS.code && postResponse?.result != null){
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

        if (postResponse?.code != StatusCode.SUCCESS.code && postResponse != null){
            val sms = StatusCode.fromStatusResId(postResponse?.code ?: 0)
            Toast.makeText(
                context,
                sms,
                Toast.LENGTH_LONG
            ).show()
        }

        /*TODO: Reset postResponse*/
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
            onCreatePost = {
                creatingPostVM.onEvent(CreatingPostEvent.Submit)
                /*TODO: Implement creating post*/
            }
        )
    }
}



// endregion