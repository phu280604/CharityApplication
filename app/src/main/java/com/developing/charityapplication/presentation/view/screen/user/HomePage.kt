package com.developing.charityapplication.presentation.view.screen.user

import androidx.compose.runtime.Composable
import com.developing.charityapplication.presentation.view.component.post.PostComponent
import com.developing.charityapplication.presentation.view.component.post.PostConfig

@Composable
fun HomePageScreen(){
    val config = PostConfig()
    repeat(2) { PostComponent(config)
        .BaseDecorate {  } }
}