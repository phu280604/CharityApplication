package com.developing.charityapplication.presentation.view.navigate.userNav.destination

import com.developing.charityapplication.domain.model.profileModel.RequestUpdateProfileM
import okhttp3.MultipartBody

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

    object EditPostPage: PostDestinations(route = "EditPostPage")
}

sealed class DonationDestinations(val route: String){
    object Destination: DonationDestinations(route = "DonationDes")

    object DonationPage: DonationDestinations(route = "DonationPage")

    object PaymentPage: DonationDestinations(route = "PaymentPage")
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