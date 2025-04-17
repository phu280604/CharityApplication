@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.asIntState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.domain.model.postModel.ResponsePostM
import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import com.developing.charityapplication.domain.model.profileModel.ResponseProfilesM
import com.developing.charityapplication.presentation.view.activity.LoginActivity
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.component.post.builder.PostComponentBuilder
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.ProfilePage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.EditProfilePage
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.profile.EditProfileViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.ProfileScreenViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.postViewModel.PostViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.profileViewModel.ProfileViewModel
import kotlinx.coroutines.launch

// region --- Methods ---

@Composable
fun HeaderProfile(navController: NavHostController){
    // region -- List Title Top App Bar --
    val listProfile = listOf(
        Pair(R.string.profile_page, Icons.Default.AccountCircle),
        Pair(R.string.edit_prodile, Icons.Default.Edit),
        Pair(R.string.more_option_profile, Icons.Default.List),
        Pair(R.string.logout, Icons.Default.ExitToApp),
    )

    val context = LocalContext.current
    // endregion

    // region -- ViewModel --
    val profileScreenVM: ProfileScreenViewModel = hiltViewModel()
    // endregion

    // region -- Value ViewModel --
    val state by profileScreenVM.selectedIndexState.asIntState()

    var showBottomSheet by remember { mutableStateOf(false) }
    // endregion

    // region -- Value Navigatetion --
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    // endregion

    // region -- Back Navigate Handle --
    BackHandler(enabled = currentRoute != ProfilePage.route) {
        profileScreenVM.changeSelectedIndex(0)
        navController.popBackStack()
        Log.d("backHandler", "profile")
    }
    // endregion

    // region -- Update Selected Index When PopBackStack --
    LaunchedEffect(navBackStackEntry) {
        navBackStackEntry?.savedStateHandle?.getLiveData<Int>("selectedIndex")?.observeForever { index ->
            index?.let {
                profileScreenVM.changeSelectedIndex(it)
                navBackStackEntry?.savedStateHandle?.remove<Int>("selectedIndex")
            }
        }
    }
    // endregion

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(listProfile[state].first),
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
                    imageVector = listProfile[state].second,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
            },
            actions = {
                IconButton(
                    onClick = {
                        showBottomSheet = true
                    }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null
                    )
                    if (showBottomSheet)
                        MenuOpitionProfile(
                            listProfile,
                            onChangeState = {
                                showBottomSheet = false
                            },
                            onNavigate = {
                                indexItem ->
                                profileScreenVM.changeSelectedIndex(indexItem)

                                val route = when(listProfile.get(indexItem).first){
                                    R.string.edit_prodile -> EditProfilePage.route
                                    R.string.logout -> {
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                        val activity = (context as? Activity)
                                        activity?.finish()
                                        return@MenuOpitionProfile
                                    }
                                    else -> ProfilePage.route
                                }

                                navController.navigate(route){
                                    popUpTo(ProfilePage.route){
                                        inclusive = false
                                    }

                                    launchSingleTop = true
                                }
                                /*TODO: Implement option profile navigate*/
                            }
                        )
                }
            },
        )

    }
}

@Composable
fun ProfilePageScreen(
    navController: NavHostController,
    editProfileVM: EditProfileViewModel
){
    // region -- Value Default --
    val context = LocalContext.current
    // endregion

    // region -- ViewModel --
    val profileInfoVM: ProfileViewModel = hiltViewModel()
    val postVM: PostViewModel = hiltViewModel()
    // endregion

    // region -- State ViewModel --
    val profileInfo by profileInfoVM.profileResponse.collectAsState()
    val postsInfo by postVM.postsResponse.collectAsState()

    val profileId by DataStoreManager.getProfileId(context).collectAsState(initial = null)

    val scrollState = rememberScrollState()

    val navCurrent = navController.currentBackStackEntry?.arguments
    // endregion

    // region -- Call API --
    LaunchedEffect(Unit) {
        if (!profileId.isNullOrEmpty())
        {
            profileInfoVM.getProfileByProfileId(profileId!!)
        }
    }
    // endregion

    if (profileInfo?.code == 1000 || postsInfo?.code == 1000){
        editProfileVM.setEditProfileData(
            context = context,
            profileId = profileId ?: "",
            profileInfo = RequestUpdateProfileM(
                lastName = profileInfo?.result?.lastName ?: "",
                firstName = profileInfo?.result?.firstName ?: "",
                username = profileInfo?.result?.username ?: "",
                location = profileInfo?.result?.location ?: "",
            ),
            avatar = profileInfo?.result?.avatarUrl
        )
        BodyProfile(
            profile = profileInfo?.result ?: ResponseProfilesM(),
            posts = postsInfo?.result ?: emptyList(),
            modifier = Modifier
                .background(color = AppColorTheme.surface)
                .verticalScroll(scrollState))
    }

}

// region -- Body Section
@Composable
fun BodyProfile(
    profile: ResponseProfilesM?,
    posts: List<ResponsePostM>,
    modifier: Modifier
){
    val avatar = if(profile?.avatarUrl == null)
        painterResource(id = R.drawable.avt_young_girl)
    else
        rememberAsyncImagePainter(profile.avatarUrl)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppColorTheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // region -- Profile Info --
            /*TODO: Implement profile information*/
            DetailsProfile(
                userBackground = avatar,
                username = profile?.lastName + " " + profile?.firstName,
                location = if(profile?.location == null) "" else profile.location,
                followingAmount = 0,
                followerAmount = 0
            )
            // endregion

            // region -- Category Post --
            /*TODO: Implement api category post*/
            CategoryPost(
                onClick = {  }
            )
            // endregion

            HorizontalDivider(
                thickness = 1.dp,
                color = AppColorTheme.surface
            )

            // region -- Post --
            posts.forEachIndexed {
                index, item ->
                PostComponentBuilder()
                    .withConfig(
                        PostConfig(
                            userbackground = avatar,
                            username = profile?.lastName + " " + profile?.firstName,
                            content = item.content,
                            fileIds = item.fileIds,
                            timeAgo = item.createdAt,
                            donationValue = "0"
                        )
                    )
                    .build()
                    .BaseDecorate {  }
            }
            // endregion
        }
    }
}

@Composable
fun DetailsProfile(
    userBackground: Painter,
    username: String,
    location: String,
    followingAmount: Int,
    followerAmount: Int
){
    Column(
        modifier = Modifier.padding(top = 24.dp, start = 24.dp, end = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = userBackground,
            contentDescription = null,
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
                .border(width = 0.5f.dp, color = AppColorTheme.onPrimary, shape = CircleShape)
        )
        Text(
            text = username,
            style = AppTypography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = AppColorTheme.onPrimary
        )
        Text(
            text = location,
            style = AppTypography.labelMedium.copy(
                fontWeight = FontWeight.Light
            ),
            color = AppColorTheme.onPrimary
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(color = AppColorTheme.primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DetailItems(
                amount =  followingAmount,
                title =  stringResource(R.string.following_users),
                modifier =  Modifier.weight(1f)
            )
            VerticalDivider(
                modifier = Modifier.height(24.dp),
                thickness = 1.dp,
                color = AppColorTheme.surface
            )
            DetailItems(
                amount =  followerAmount,
                title =  stringResource(R.string.follower),
                modifier =  Modifier.weight(1f)
            )
        }
    }
}

// region -- Profile Component --
@Composable
fun CategoryPost(
    onClick: () -> Unit
){
    val listCate = listOf(
        R.drawable.post,
        R.drawable.lock,
        R.drawable.save_instagram
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
    ) {
        listCate.forEachIndexed {
            index, item ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .clickable(
                        role = Role.Button,
                        onClick = {
                            selectedIndex = index
                            onClick()
                        }
                    ),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = item),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    colorFilter = ColorFilter
                        .tint(
                            color = if(selectedIndex == index)
                                AppColorTheme.secondary
                            else
                                AppColorTheme.onPrimary
                        )
                )
            }
            VerticalDivider(
                thickness = 0.5.dp,
                color = AppColorTheme.surface
            )
        }
    }
}

@Composable
fun DetailItems(
    amount: Int,
    title: String,
    modifier: Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = amount.toString(),
            style = AppTypography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = AppColorTheme.onPrimary
        )
        Text(
            text = title,
            style = AppTypography.bodyMedium,
            color = AppColorTheme.onPrimary
        )
    }
}

@Composable
fun MenuOpitionProfile(
    listItems: List<Pair<Int, ImageVector>>,
    onChangeState: () -> Unit,
    onNavigate: (Int) -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = { onChangeState },
        containerColor = AppColorTheme.primary
    ) {
        listItems.forEachIndexed {
            index, item ->
            if (index == 0) return@forEachIndexed
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(32.dp)
                    .clickable(
                        role = Role.Button,
                        onClick = {
                            onNavigate(index)
                            scope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    onChangeState()
                                }
                            }
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start)
            ) {
                Icon(
                    imageVector = item.second,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = AppColorTheme.onPrimary
                )
                Text(
                    text = stringResource(item.first),
                    style = AppTypography.titleMedium,
                    color = AppColorTheme.onPrimary
                )
            }
        }
    }

}

// region -- Fake Data --
@Composable
fun fakeDataPostProfile(): List<PostConfig>{
    val avatar = painterResource(id = R.drawable.avt_young_girl)
    return remember {
        listOf(
            PostConfig(
                userbackground = avatar,
                username = "Nguyễn Văn A",
                content = "Hôm nay là một ngày tuyệt vời! Cùng nhau làm điều ý nghĩa nhé! ❤️",
                donationValue = "500.000 VNĐ",
                dateRange = "1-5 Tháng 4",
                likeCount = "120",
                commentCount = "30",
                shareCount = "15"
            ),
            PostConfig(
                userbackground = avatar,
                username = "Nguyễn Văn A",
                content = "Chúng ta hãy chung tay giúp đỡ những hoàn cảnh khó khăn!",
                donationValue = "1.000.000 VNĐ",
                dateRange = "10-15 Tháng 4",
                likeCount = "250",
                commentCount = "50",
                shareCount = "30"
            ),
            PostConfig(
                userbackground = avatar,
                username = "Nguyễn Văn A",
                content = "Mỗi hành động nhỏ của bạn đều có thể tạo ra sự khác biệt lớn!",
                donationValue = "750.000 VNĐ",
                dateRange = "5-10 Tháng 4",
                likeCount = "180",
                commentCount = "40",
                shareCount = "20"
            ),
            PostConfig(
                userbackground = avatar,
                username = "Nguyễn Văn A",
                content = "Cảm ơn mọi người đã luôn đồng hành và ủng hộ!",
                donationValue = "2.000.000 VNĐ",
                dateRange = "20-25 Tháng 4",
                likeCount = "400",
                commentCount = "100",
                shareCount = "50"
            )
        )
    }
}
// endregion
// endregion
// endregion

// endregion