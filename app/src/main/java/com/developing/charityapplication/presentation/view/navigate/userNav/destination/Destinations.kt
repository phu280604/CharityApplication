package com.developing.charityapplication.presentation.view.navigate.userNav.destination

import kotlinx.serialization.Serializable

// region --- User Pages ---

@Serializable
object HomeDestinations{
    @Serializable
    object HomePage
}

@Serializable
object NotificationDestinations{
    @Serializable
    object NotificationPage
}


@Serializable
object FollowerDestinations{
    @Serializable
    object FollowerPage
}

@Serializable
object PostDestinations{
    @Serializable
    object CreatePostPage
}

@Serializable
object MessageDestinations{
    @Serializable
    object MessagerPage
}

@Serializable
object ProfileDestinations{
    @Serializable
    object ProfilePage
}

// endregion