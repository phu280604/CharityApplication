package com.developing.charityapplication.presentation.view.component.post

data class PostConfig (
    var userbackground: Int = -1,
    var username: String = "",
    var timeAgo: Int = -1,
    var content: String = "",
    var donationValue: String = "",
    var dateRange: String = "",
    var likeCount: String = "",
    var commentCount: String = "",
    var shareCount: String = ""
)