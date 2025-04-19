package com.developing.charityapplication.presentation.view.navigate.userNav

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.*
import com.developing.charityapplication.presentation.view.screen.user.posts.*
import com.developing.charityapplication.presentation.view.screen.user.follower.*
import com.developing.charityapplication.presentation.view.screen.user.home.*
import com.developing.charityapplication.presentation.view.screen.user.message.*
import com.developing.charityapplication.presentation.view.screen.user.profile.*
import androidx.compose.animation.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.creatingPost.CreatingPostViewModel
import com.developing.charityapplication.presentation.viewmodel.screenViewModel.profile.EditProfileViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavigationUsersApplication(modifier: Modifier, navController: NavHostController) {
    val sharedProfileInfo: EditProfileViewModel = hiltViewModel()
    val sharedPostInfo: CreatingPostViewModel = hiltViewModel()

    AnimatedNavHost(
        navController = navController,
        startDestination = HomeDestinations.Destination.route,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it }, // slide from right
                animationSpec = tween(300)
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it }, // slide to left
                animationSpec = tween(300)
            )
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it }, // slide back from left
                animationSpec = tween(300)
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it }, // slide out to right
                animationSpec = tween(300)
            )
        }
    ) {
        // region -- HomePage --
        navigation(
            startDestination = HomeDestinations.HomePage.route,
            route = HomeDestinations.Destination.route
        ) {
            composable(HomeDestinations.HomePage.route) {
                HomePageScreen()
            }
            composable(HomeDestinations.NotificationPage.route) {
                NotificationPageScreen()
            }
        }
        // endregion

        // region -- FollowerPage --
        navigation(
            startDestination = FollowerDestinations.FollowerPage.route,
            route = FollowerDestinations.Destination.route
        ) {
            composable(FollowerDestinations.FollowerPage.route) {
                FollowerPageScreen()
            }
        }
        // endregion

        // region -- PostPage --
        navigation(
            startDestination = PostDestinations.CreatePostPage.route,
            route = PostDestinations.Destination.route
        ) {
            composable(PostDestinations.CreatePostPage.route) {
                CreatingPostPageScreen(navController)
            }

            composable(PostDestinations.EditPostPage.route) {
                EditPostPageScreen(navController, sharedPostInfo)
            }
        }
        // endregion

        // region -- MessagePage --
        navigation(
            startDestination = MessageDestinations.MessagerPage.route,
            route = MessageDestinations.Destination.route
        ) {
            composable(MessageDestinations.MessagerPage.route) {
                MessagePageScreen()
            }
        }
        // endregion

        // region -- ProfilePage --
        navigation(
            startDestination = ProfileDestinations.ProfilePage.route,
            route = ProfileDestinations.Destination.route
        ) {

            composable(ProfileDestinations.ProfilePage.route) {
                ProfilePageScreen(navController, sharedProfileInfo, sharedPostInfo)
            }
            composable(ProfileDestinations.EditProfilePage.route) {
                EditProfileScreen(navController, sharedProfileInfo)
            }
        }
        // endregion
    }
}
