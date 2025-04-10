package com.developing.charityapplication.presentation.view.navigate.userNav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.developing.charityapplication.presentation.view.navigate.userNav.destination.*
import com.developing.charityapplication.presentation.view.screen.user.*

@Composable
fun NavigationUsersApplication(modifier: Modifier, navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = HomeDestinations,
        modifier = modifier
    ){
        // region -- NotificationPage --
        navigation<NotificationDestinations>(startDestination = NotificationDestinations.NotificationPage){
            composable<NotificationDestinations.NotificationPage>{
                NotificationPageScreen()
            }
        }
        // endregion

        // region -- HomePage --
        navigation<HomeDestinations>(startDestination = HomeDestinations.HomePage){
            composable<HomeDestinations.HomePage>{
                HomePageScreen()
            }
        }
        // endregion

        // region -- FollowerPage --
        navigation<FollowerDestinations>(startDestination = FollowerDestinations.FollowerPage){
            composable<FollowerDestinations.FollowerPage>{
                Log.d("Screen", "FollowerPage")
                FollowerPageScreen()
            }
        }
        // endregion

        // region -- PostPage --
        navigation<PostDestinations>(startDestination = PostDestinations.CreatePostPage){
            composable<PostDestinations.CreatePostPage>{
                Log.d("Screen", "CreatingPostPage")
                CreatingPostPageScreen()
            }
        }
        // endregion

        // region -- MessagePage --
        navigation<MessageDestinations>(startDestination = MessageDestinations.MessagerPage){
            composable<MessageDestinations.MessagerPage>{
                Log.d("Screen", "SMSPage")
                MessagePageScreen()
            }
        }
        // endregion

        // region -- ProfilePage --
        navigation<ProfileDestinations>(startDestination = ProfileDestinations.ProfilePage){
            composable<ProfileDestinations.ProfilePage>{
                Log.d("Screen", "ProfilePage")
                ProfilePageScreen()
            }
        }
        // endregion
    }
}