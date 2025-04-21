package com.developing.charityapplication.presentation.view.screen.user.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.infrastructure.utils.DefaultValue
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.component.post.builder.PostComponentBuilder
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.postViewModel.PostViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.profileViewModel.ProfileViewModel

// region --- Methods ---

@Composable
fun HomePageScreen(){
    // region -- Value Default --
    val context = LocalContext.current
    // endregion

    // region -- ViewModel --
    val postVM: PostViewModel = hiltViewModel()
    // endregion

    // region -- State --

    val allPosts by postVM.allPostsResponse.collectAsState()
    val allProfile by postVM.allProfilesResponse.collectAsState()

    val profileId by DataStoreManager.getProfileId(context).collectAsState(initial = null)

    var selectedImage by remember { mutableStateOf<String?>(null) }
    // endregion

    // region -- Call Api --
    LaunchedEffect(profileId) {
        if(profileId != null && allPosts.isNullOrEmpty())
            postVM.getAllPosts(profileId ?: "")
    }
    // endregion

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .verticalScroll(scrollState)
    ){
        if(!allPosts.isNullOrEmpty() && !allProfile.isNullOrEmpty()){
            allPosts?.forEach{ post ->
                val getInfo = allProfile?.find { it.profileId == post.profileId }
                val avatar = if (getInfo?.avatarUrl.isNullOrEmpty())
                    painterResource(id = DefaultValue.avatar)
                else rememberAsyncImagePainter(getInfo.avatarUrl)
                PostComponentBuilder()
                    .withConfig(
                        PostConfig(
                            content = post.content,
                            username = "${getInfo?.lastName} ${getInfo?.firstName}",
                            userbackground = avatar,
                            fileIds = post.fileIds,
                            timeAgo = post.createdAt,
                            maximizeImage = { imageUrl -> selectedImage = imageUrl }
                        )
                    )
                    .build()
                    .BaseDecorate {  }
            }
        }
    }
    // region -- Overlay Maximize Image --
    selectedImage?.let { image ->
        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .background(AppColorTheme.onPrimary.copy(alpha = 0.85f))
                .clickable { selectedImage = null },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f),
                contentScale = ContentScale.Fit
            )
        }
    }
    // endregion
}