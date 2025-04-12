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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.ShowSMS
import com.developing.charityapplication.presentation.view.component.navItem.NavItemConfig
import com.developing.charityapplication.presentation.view.navigate.userNav.NavigationUsersApplication
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.FollowerDestinations.FollowerPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations.HomePage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.HomeDestinations.NotificationPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.MessageDestinations.MessagerPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.PostDestinations.CreatePostPage
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.ProfileDestinations.ProfilePage
import com.developing.charityapplication.presentation.view.screen.user.creatingPost.HeaderCreatingPost
import com.developing.charityapplication.presentation.view.screen.user.follower.HeaderFollower
import com.developing.charityapplication.presentation.view.screen.user.home.HeaderNotification
import com.developing.charityapplication.presentation.view.screen.user.profile.HeaderProfile
import com.developing.charityapplication.presentation.view.theme.*
import com.developing.charityapplication.presentation.viewmodel.activityViewModel.UserAppViewModel
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserAppActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            UserAppUI()
        }
    }
    
    // endregion

    // region --- Methods ---

    @Composable
    fun UserAppUI (){
        val navController = rememberAnimatedNavController()
        val userAppVM: UserAppViewModel = hiltViewModel()

        val selectedState by userAppVM.selectedIndexState.asIntState()

        var showMessage = remember { mutableStateOf(false) }
        var funcTitle by remember { mutableIntStateOf(0) }

        HeartBellTheme {
            Scaffold(
                topBar = {
                    Header(
                        navController = navController,
                        selectedIndex = selectedState,
                        onChangeState = { index -> userAppVM.changeSelectedIndex(index) }
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
                        Footer(
                            navController,
                            selectedState,
                            onChangeState = { index -> userAppVM.changeSelectedIndex(index) },
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
                NavigationUsersApplication(Modifier.padding(innerPadding), navController)

                ShowSMS(
                    funcTitle = if (funcTitle != 0) stringResource(funcTitle) else null,
                    visible = showMessage.value,
                    onDismiss = { showMessage.value = false }
                )
            }
        }
    }

    // region -- UI Section --
    @Composable
    fun Header(
        navController: NavHostController,
        selectedIndex: Int,
        onChangeState: (Int) -> Unit
    ) {
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
                0 -> {
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
                                        onChangeState(-1)
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
                1 -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        onChangeState(0)
                        navController.popBackStack()
                    }

                    HeaderFollower(navController)
                }
                2 -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        onChangeState(0)
                        navController.popBackStack()
                    }

                    HeaderCreatingPost(navController)
                }
                4 -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        onChangeState(0)
                        navController.popBackStack()
                    }

                    HeaderProfile(navController)
                }
                else -> {
                    BackHandler {
                        Log.d("backHandler", "Total")
                        onChangeState(0)
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
        onChangeState: (Int) -> Unit,
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
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            colorFilter = if (!item.skipOption)
                                ColorFilter.tint(color)
                            else null,
                            modifier = modifier
                        )
                    },
                    label = {
                        if (item.title != 0)
                            Text(
                                text = stringResource(id = item.title),
                                style = AppTypography.labelMedium
                            )
                    },
                    selected = selectedIndex == index,
                    onClick = {
                        val route = when (item.title) {
                            R.string.nav_home -> HomePage.route
                            R.string.nav_friend -> {
                                onShowMessage(item.title)
                                return@NavigationBarItem
                                FollowerPage.route
                            }
                            R.string.nav_chatting -> {
                                onShowMessage(item.title)
                                return@NavigationBarItem
                                MessagerPage.route
                            }
                            R.string.nav_profile -> ProfilePage.route
                            else -> CreatePostPage.route
                        }
                        navController.navigate(route){
                            popUpTo(HomePage.route){
                                inclusive = false
                            }

                            launchSingleTop = true
                        }
                        onChangeState(index)
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
                icon = R.drawable.avt_young_girl,
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