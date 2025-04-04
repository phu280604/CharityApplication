package com.developing.charityapplication.presentation.view.navigate.userNav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.developing.charityapplication.presentation.view.activity.UserAppActivity
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.*
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.screen.user.*

@Composable
fun NavigationUsersApplication(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomePage
    ){
        composable<HomePage>{
            UserAppActivity().UserAppUI(
                idTitle = R.string.nav_home,
                onNavClick = { HomePageScreen() }
            )
        }
        composable<FollowerPage>{
            UserAppActivity().UserAppUI(
                idTitle = R.string.nav_following,
                onNavClick = { FollowerPageScreen() }
            )
        }
        composable<CreatePostPage>{
            UserAppActivity().UserAppUI(
                idTitle = 0,
                onNavClick = { CreatingPostPageScreen() }
            )
        }
        composable<MessagerPage>{
            UserAppActivity().UserAppUI(
                idTitle = R.string.nav_chatting,
                onNavClick = { MessagePageScreen() }
            )
        }
        composable<ProfilePage>{
            UserAppActivity().UserAppUI(
                idTitle = R.string.nav_profile,
                onNavClick = { ProfilePageScreen() }
            )
        }
    }
}