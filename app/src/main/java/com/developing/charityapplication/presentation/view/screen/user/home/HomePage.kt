package com.developing.charityapplication.presentation.view.screen.user.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.infrastructure.utils.DefaultValue
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.component.post.builder.PostComponentBuilder
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.postViewModel.PostViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.profileViewModel.ProfileViewModel

// region --- Methods ---

@Composable
fun HomePageScreen(){
    // region -- Value Default --
    val context = LocalContext.current
    // endregion

    // region -- ViewModel --
    val profileVM: ProfileViewModel = hiltViewModel()
    val postVM: PostViewModel = hiltViewModel()
    // endregion

    // region -- State --

    val allPosts by postVM.allPostsResponse.collectAsState()

    val profileId by DataStoreManager.getProfileId(context).collectAsState(initial = null)
    val profilesInfo = remember { mutableStateListOf<ResponseProfilesM>() }

    var selectedImage by remember { mutableStateOf<String?>(null) }
    // endregion

    // region -- Call Api --
    LaunchedEffect(Unit) {
        postVM.getAllPosts(profileId ?: "")
    }

    LaunchedEffect(allPosts) {
        if(allPosts?.result?.count() != 0)
        {
            val profilesId = allPosts?.result?.map { it.profileId }?.distinct()
            profilesId?.forEach{ id ->
                profileVM.getProfileByProfileId(id)
                val profileInfo = profileVM.profileResponse.value?.result
                if(profileInfo != null)
                    profilesInfo.add(profileInfo)
            }
        }
    }
    // endregion

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(scrollState)
    ){
        allPosts?.result?.forEach{ post ->
            val getProfile = profilesInfo.filter { it.profileId == post.profileId }.distinct()
            val avatar = if(getProfile.first().avatarUrl.isEmpty())
                painterResource(DefaultValue.avatar)
            else rememberAsyncImagePainter(getProfile.first().avatarUrl)
            val name = getProfile.first().lastName + " " + getProfile.first().firstName
            PostComponentBuilder()
                .withConfig(
                    PostConfig(
                        userbackground = avatar,
                        username = name,
                        content = post.content,
                        fileIds = post.fileIds,
                        timeAgo = post.createdAt,
                        donationValue = "0",
                        maximizeImage = { image -> selectedImage = image }
                    )
                )
        }
    }
}