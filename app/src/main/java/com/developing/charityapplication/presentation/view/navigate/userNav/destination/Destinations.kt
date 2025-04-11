package com.developing.charityapplication.presentation.view.navigate.userNav.destination

// region --- User Pages ---

sealed class HomeDestinations(val route: String ){
    object Destination: HomeDestinations(route = "HomeDes")

    object HomePage: HomeDestinations(route = "HomePage")

    object NotificationPage: HomeDestinations(route = "NotificationPage")
}

sealed class FollowerDestinations(val route: String){
    object Destination: HomeDestinations(route = "FollowerDes")

    object FollowerPage: FollowerDestinations(route = "FollowerPage")
}

sealed class PostDestinations(val route: String){
    object Destination: HomeDestinations(route = "PostDes")

    object CreatePostPage: PostDestinations(route = "CreatePostPage")
}

sealed class MessageDestinations(val route: String){
    object Destination: HomeDestinations(route = "MessageDes")

    object MessagerPage: MessageDestinations(route = "MessagerPage")
}

sealed class ProfileDestinations(val route: String){
    object Destination: HomeDestinations(route = "ProfileDes")

    object ProfilePage: ProfileDestinations(route = "ProfilePage")

    object EditProfilePage: ProfileDestinations(route = "EditProfilePage")
}

// endregion