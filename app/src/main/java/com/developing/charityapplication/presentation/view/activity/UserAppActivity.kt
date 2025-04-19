@file:OptIn(ExperimentalAnimationApi::class)

package com.developing.charityapplication.presentation.view.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil3.compose.rememberAsyncImagePainter
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.data.dataManager.DataStoreManager
import com.developing.charityapplication.infrastructure.utils.DefaultValue
import com.developing.charityapplication.infrastructure.utils.ShowSMS
import com.developing.charityapplication.presentation.view.component.navItem.NavItemConfig
import com.developing.charityapplication.presentation.view.navigate.userNav.NavigationUsersApplication
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.FollowerDestinations.FollowerPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations.HomePage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations.NotificationPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.MessageDestinations.MessagerPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.PostDestinations.CreatePostPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.ProfilePage
import com.developing.charityapplication.presentation.view.screen.user.posts.HeaderCreatingPost
import com.developing.charityapplication.presentation.view.screen.user.follower.HeaderFollower
import com.developing.charityapplication.presentation.view.screen.user.home.HeaderNotification
import com.developing.charityapplication.presentation.view.screen.user.posts.HeaderEditPost
import com.developing.charityapplication.presentation.view.screen.user.profile.HeaderProfile
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.FooterViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.rofile.HeaderViewModel
import com.developing.charityapplication.presentation.viewmodel.serviceViewModel.profileViewModel.ProfileViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserAppActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val isEnable = intent.getBooleanExtra("isEnable", true)
        HeaderViewModel.changeSelectedIndex()
        FooterViewModel.changeSelectedIndex()

        setContent{
            UserAppUI(isEnable)
        }
    }
    
    // endregion

    // region --- Methods ---

    @Composable
    fun UserAppUI (
        isEnable: Boolean
    ){
        val profileVM: ProfileViewModel = hiltViewModel()

        val profilesResponse by profileVM.profilesResponse.collectAsState()
        val isLoading by profileVM.isLoading.collectAsState()
        val context = LocalContext.current
        var isActive by remember { mutableStateOf(isEnable) }

        LaunchedEffect(Unit) {
            if(!isActive)
                profileVM.setActiveProfile()
        }

        LaunchedEffect(isLoading) {
            if(!isLoading) {
                isActive = true
                val profileId = profilesResponse?.result?.firstOrNull()?.profileId
                val userId = profilesResponse?.result?.firstOrNull()?.userId

                if (!profileId.isNullOrEmpty() && !userId.isNullOrEmpty())
                    lifecycleScope.launch{
                        DataStoreManager.saveProfileId(context, profileId)
                        DataStoreManager.saveUserId(context, userId)
                    }

            }
        }

        if (isActive) {
            val navController = rememberAnimatedNavController()
            val state by HeaderViewModel.selectedIndexState.collectAsState()
            val stateBottom by FooterViewModel.selectedIndexState.collectAsState()

            var showMessage = remember { mutableStateOf(false) }
            var funcTitle by remember { mutableIntStateOf(0) }

            HeartBellTheme {
                Scaffold(
                    topBar = {
                        Header(
                            navController = navController,
                            selectedIndex = state
                        )
                    },
                    bottomBar = {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .background(color = AppColorTheme.primary)
                                .shadow(
                                    elevation = 16.dp, // độ đổ bóng
                                    shape = RectangleShape,
                                    clip = false
                                ),
                            color = AppColorTheme.primary,
                            contentColor = AppColorTheme.secondary
                        ) {
                            Log.d("ProfileAvt", "Avt: ${profilesResponse?.result?.firstOrNull()?.avatarUrl}")
                            Footer(
                                navController = navController,
                                selectedIndex = stateBottom,
                                avatar = if(!profilesResponse?.result?.firstOrNull()?.avatarUrl.isNullOrEmpty())
                                    rememberAsyncImagePainter(profilesResponse?.result?.firstOrNull()?.avatarUrl)
                                else
                                    painterResource(DefaultValue.avatar),
                                onShowMessage = {
                                        index ->
                                    showMessage.value = true
                                    funcTitle = index
                                }
                            )
                        }
                    },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
                ){ innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(color = AppColorTheme.primary)
                    ){
                        NavigationUsersApplication(Modifier.fillMaxSize(), navController)
                    }

                    ShowSMS(
                        funcTitle = if (funcTitle != 0) stringResource(funcTitle) else null,
                        visible = showMessage.value,
                        onDismiss = { showMessage.value = false }
                    )
                }
            }
        }
    }

    // region -- UI Section --
    @Composable
    fun Header(
        navController: NavHostController,
        selectedIndex: Int
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        LaunchedEffect(navBackStackEntry) {
            navBackStackEntry?.savedStateHandle?.getLiveData<Int>("selectedIndex")?.observeForever { index ->
                index?.let {
                    HeaderViewModel.changeSelectedIndex()
                    FooterViewModel.changeSelectedIndex()
                    navBackStackEntry?.savedStateHandle?.remove<Int>("selectedIndex")
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(color = AppColorTheme.primary),
            shadowElevation = 4.dp,
            color = AppColorTheme.primary,
            contentColor = AppColorTheme.secondary
        ){
            when (selectedIndex) {
                R.string.nav_home -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp, 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // region - Search Bar --
                        var searchText by remember { mutableStateOf("") }

                        BasicTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            textStyle = AppTypography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            singleLine = true,
                            modifier = Modifier
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(
                                        alpha = 0.32f
                                    ),
                                    shape = CircleShape
                                )
                                .size(
                                    width = 230.dp,
                                    height = 32.dp
                                )
                                .clip(CircleShape),
                            decorationBox = { innerTextField ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = MaterialTheme.colorScheme.onSurface,
                                            shape = CircleShape
                                        )
                                        .padding(start = 16.dp, end = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        if (searchText.isEmpty())
                                            Text(
                                                text = stringResource(id = R.string.search),
                                                style = AppTypography.bodyMedium.copy(
                                                    color = MaterialTheme.colorScheme.secondaryContainer.copy(
                                                        alpha = 0.32f
                                                    )
                                                )
                                            )
                                        else
                                            innerTextField()
                                    }

                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_search),
                                        contentDescription = "search",
                                        tint = MaterialTheme.colorScheme.secondaryContainer.copy(
                                            alpha = 0.32f
                                        ),
                                        modifier = Modifier
                                            .size(24.dp)
                                    )

                                }
                            }
                        )
                        // endregion

                        // region - Notification Button -
                        val notificationButton = ButtonComponentBuilder()
                            .withConfig(
                                newConfig = defaultButton.copy(
                                    onClick = {
                                        HeaderViewModel.changeSelectedIndex(-1)
                                        FooterViewModel.changeSelectedIndex()
                                        navController.navigate(route = NotificationPage.route){
                                            popUpTo(route = HomePage.route){
                                                inclusive = false
                                            }

                                            launchSingleTop = true
                                        }
                                        /*TODO: Implement notification logic*/
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(
                                            width = 0.5.dp,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            shape = RoundedCornerShape(8.dp)
                                        ),
                                    content = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_bell),
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onPrimary,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                )
                            )
                            .build()
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            notificationButton.BaseDecorate { }
                            Badge(
                                modifier = Modifier
                                    .size(8.dp)
                                    .offset(x = (16).dp, y = (-16).dp),
                                containerColor = MaterialTheme.colorScheme.onError
                            )
                        }
                        // endregion
                    }
                }
                R.string.nav_friend -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        HeaderViewModel.changeSelectedIndex()
                        FooterViewModel.changeSelectedIndex()
                        navController.popBackStack()
                    }

                    HeaderFollower(navController)
                }
                R.string.creating_page -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        HeaderViewModel.changeSelectedIndex()
                        FooterViewModel.changeSelectedIndex()
                        navController.popBackStack()
                    }

                    HeaderCreatingPost(navController)
                }
                R.string.nav_profile -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        HeaderViewModel.changeSelectedIndex()
                        FooterViewModel.changeSelectedIndex()
                        navController.popBackStack()
                    }

                    HeaderProfile(navController)
                }
                R.string.edit_post -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        HeaderViewModel.changeSelectedIndex(R.string.nav_profile)
                        FooterViewModel.changeSelectedIndex(4)
                        navController.popBackStack()
                    }

                    HeaderEditPost(navController)
                }
                else -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        HeaderViewModel.changeSelectedIndex()
                        FooterViewModel.changeSelectedIndex()
                        navController.popBackStack()
                    }

                    HeaderNotification(navController)
                }
            }
        }
    }

    @Composable
    fun Footer(
        navController: NavController,
        selectedIndex: Int,
        avatar: Painter,
        onShowMessage: (Int) -> Unit
    ) {
        var navItem = navItems()

        NavigationBar(
            containerColor = AppColorTheme.primary,
            modifier = Modifier
                .fillMaxSize()
        ) {
            navItem.forEachIndexed { index, item ->
                var color = if (item.title == 0) AppColorTheme.primary
                else if (selectedIndex == index) AppColorTheme.secondary
                else AppColorTheme.onPrimary

                val modifier = if (item.title == 0) Modifier
                    .size(40.dp)
                    .background(
                        color = AppColorTheme.secondary,
                        shape = RoundedCornerShape(8.dp)
                    )
                else Modifier.size(32.dp)

                NavigationBarItem(
                    icon = {
                        val newModifier = if(item.title == navItems().last().title)
                            modifier
                                .clip(CircleShape)
                                .border(
                                    width = 0.5f.dp,
                                    color = if(selectedIndex == 4)
                                        AppColorTheme.secondary
                                    else AppColorTheme.onPrimary,
                                    shape = CircleShape
                                )
                        else
                            modifier
                                .clip(CircleShape)
                        Image(
                            painter = if(item.title == navItems().last().title) avatar
                            else painterResource(item.icon),
                            contentDescription = null,
                            colorFilter = if (!item.skipOption)
                                ColorFilter.tint(color)
                            else null,
                            contentScale = ContentScale.Inside,
                            modifier = newModifier
                        )

                    },
                    label = {
                        if (item.title != 0)
                            Text(
                                text = stringResource(id = item.title),
                                style = AppTypography.labelMedium,
                                color = color
                            )
                    },
                    selected = selectedIndex == index,
                    onClick = {
                        val route = when (item.title) {
                            R.string.nav_home -> {
                                HeaderViewModel.changeSelectedIndex(R.string.nav_home)
                                FooterViewModel.changeSelectedIndex(0)
                                HomePage.route
                            }
                            R.string.nav_friend -> {
                                onShowMessage(item.title)
                                return@NavigationBarItem
                                HeaderViewModel.changeSelectedIndex(R.string.nav_friend)
                                FooterViewModel.changeSelectedIndex(1)
                                FollowerPage.route
                            }
                            R.string.nav_chatting -> {
                                onShowMessage(item.title)
                                return@NavigationBarItem
                                HeaderViewModel.changeSelectedIndex(R.string.nav_chatting)
                                FooterViewModel.changeSelectedIndex(3)
                                MessagerPage.route
                            }
                            R.string.nav_profile -> {
                                HeaderViewModel.changeSelectedIndex(R.string.nav_profile)
                                FooterViewModel.changeSelectedIndex(4)
                                ProfilePage.route
                            }
                            else -> {
                                HeaderViewModel.changeSelectedIndex(R.string.creating_page)
                                FooterViewModel.changeSelectedIndex(2)
                                CreatePostPage.route
                            }
                        }
                        navController.navigate(route){
                            popUpTo(HomePage.route){
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors().copy(
                        selectedIndicatorColor = Color.Transparent,
                        selectedTextColor = AppColorTheme.secondary
                    )
                )
            }
        }
    }

    fun navItems(): List<NavItemConfig>{
        return listOf(
            NavItemConfig(
                title = R.string.nav_home,
                icon = R.drawable.ic_nav_home,
            ),
            NavItemConfig(
                title = R.string.nav_friend,
                icon = R.drawable.ic_nav_users,
            ),
            NavItemConfig(
                icon = R.drawable.ic_nav_plus,
            ),
            NavItemConfig(
                title = R.string.nav_chatting,
                icon = R.drawable.ic_nav_chattting,
                badgeCount = 0
            ),
            NavItemConfig(
                title = R.string.nav_profile,
                icon = DefaultValue.avatar,
                skipOption = true,
            )
        )
    }

    // region -- Component Section --
    fun createButtonDefault() : ButtonConfig{
        return ButtonComponentBuilder()
            .withConfig(
                ButtonConfig(
                    modifier = Modifier
                        .size(
                            width = 64.dp,
                            height = 48.dp
                        ),
                )
            )
            .build()
            .getConfig()
    }
    // endregion
    // endregion

    // endregion

    // region  --- Fields ---

    private val defaultButton: ButtonConfig by lazy { createButtonDefault() }

    // endregion

}