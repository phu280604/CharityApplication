@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.profile

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.component.post.builder.PostComponentBuilder
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.ProfilePage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.EditProfilePage
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.ProfileScreenViewModel
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
    // endregion

    // region -- Value ViewModel --
    val profileScreenVM: ProfileScreenViewModel = hiltViewModel()

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
fun ProfilePageScreen(){
    val fakePostProfile = fakeDataPostProfile()
    val scrollState = rememberScrollState()

    BodyProfile(
        fakePostProfile,
        Modifier
            .background(color = AppColorTheme.surface)
            .verticalScroll(scrollState))
}

// region -- Body Section
@Composable
fun BodyProfile(posts: List<PostConfig>, modifier: Modifier){
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
                userBackground = painterResource(posts[0].userbackground),
                username = posts[0].username,
                followingAmount = 24,
                followerAmount = 2
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
                        item
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
            modifier = Modifier.size(96.dp)
        )
        Text(
            text = username,
            style = AppTypography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
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
fun DetailItems(amount: Int, title: String, modifier: Modifier){
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
    return remember {
        listOf(
            PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "Nguyễn Văn A",
                timeAgo = 5,
                content = "Hôm nay là một ngày tuyệt vời! Cùng nhau làm điều ý nghĩa nhé! ❤️",
                donationValue = "500.000 VNĐ",
                dateRange = "1-5 Tháng 4",
                likeCount = "120",
                commentCount = "30",
                shareCount = "15"
            ),
            PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "Nguyễn Văn A",
                timeAgo = 10,
                content = "Chúng ta hãy chung tay giúp đỡ những hoàn cảnh khó khăn!",
                donationValue = "1.000.000 VNĐ",
                dateRange = "10-15 Tháng 4",
                likeCount = "250",
                commentCount = "50",
                shareCount = "30"
            ),
            PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "Nguyễn Văn A",
                timeAgo = 15,
                content = "Mỗi hành động nhỏ của bạn đều có thể tạo ra sự khác biệt lớn!",
                donationValue = "750.000 VNĐ",
                dateRange = "5-10 Tháng 4",
                likeCount = "180",
                commentCount = "40",
                shareCount = "20"
            ),
            PostConfig(
                userbackground = R.drawable.avt_young_girl,
                username = "Nguyễn Văn A",
                timeAgo = 20,
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