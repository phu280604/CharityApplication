package com.developing.charityapplication.presentation.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.post.PostComponent
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.theme.AppTypography

class UserAppActivity : ComponentActivity() {

    // region --- Overrides ---

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            val scrollState = rememberScrollState()
            HeartBellTheme {
                Scaffold(
                    topBar = { Header() },
                    bottomBar = { Footer() },
                    modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
                ){ innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.primary
                            )
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .verticalScroll(scrollState)
                    ) {

                    }
                }
            }
        }
    }
    
    // endregion

    // region --- Methods ---

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

    @Composable
    fun SearchBar() {
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
                    ){
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
    }

    @Composable
    fun NotificationBadge(){
        val notificationButton = ButtonComponentBuilder()
            .withConfig(
                newConfig = defaultButton.copy(
                    onClick = { /*TODO: Implement notification logic*/ },
                    modifier = Modifier
                        .size(40.dp)
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
        ){
            notificationButton.BaseDecorate {  }
            Badge(
                modifier = Modifier
                    .size(8.dp)
                    .offset(x = 8.dp, y = (-8).dp),
                containerColor = MaterialTheme.colorScheme.onError
            )
        }
    }

    @Composable
    fun NavigateItem(
        modifier: Modifier = Modifier,
        isSelected: Boolean = false,
        text: Int = 0,
        isIcon: Boolean = true,
        iconRes: Int = -1,
        imageRes: Int = R.drawable.avt_young_girl
    ){
        val color = if (isSelected && text >= 0) MaterialTheme.colorScheme.secondary
        else if (text < 0) MaterialTheme.colorScheme.onSecondary
        else MaterialTheme.colorScheme.onPrimary

        var lateModifier = modifier
        if (text < 0)
            lateModifier = Modifier
                .size(
                    width = 56.dp,
                    height = 32.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )


        Column(
            modifier = modifier
                .width(64.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isIcon && iconRes >= 0){
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    tint = color,
                    modifier = lateModifier
                )
            }
            else {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = lateModifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = CircleShape
                        )
                )
            }

            if (text >= 0){
                Text(
                    text = stringResource(id = text),
                    style = AppTypography.labelMedium,
                    color = color
                )
            }
        }
    }

    // region -- UI Section --
    @Composable
    fun Header() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(64.dp, 80.dp),
            shadowElevation = 4.dp,
            color = MaterialTheme.colorScheme.primary
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SearchBar()
                    NotificationBadge()
                }
            }
        }
    }

    @Composable
    fun Footer(){

        val navOptions = listOf(
            Pair(R.string.nav_home, R.drawable.ic_nav_home),
            Pair(R.string.nav_following, R.drawable.ic_nav_users),
            Pair(-1, R.drawable.ic_nav_plus),
            Pair(R.string.nav_chatting, R.drawable.ic_nav_chattting),
            Pair(R.string.nav_profile, -1),
        )
        val (selectedOption, onOptionSelected) = remember { mutableStateOf(navOptions[0]) }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navOptions.forEach {
                    item ->
                    var isSelected = item == selectedOption
                    var isIcon = item.second >= 0

                    NavigateItem(
                        isSelected = isSelected,
                        text = item.first,
                        isIcon = isIcon,
                        iconRes = item.second,
                        modifier = Modifier
                            .selectable(
                                selected = (item.first == selectedOption.first),
                                onClick = {
                                    /*TODO: Implement nav home logic*/
                                    onOptionSelected(item)
                                },
                                role = Role.RadioButton
                            )
                    )
                }
            }
        }
    }

    // region -- Main UI Overview --
    @Preview
    @Composable
    fun MainUIOverview(){
        val scrollState = rememberScrollState()
        HeartBellTheme {
            Scaffold(
                topBar = { Header() },
                bottomBar = { Footer() },
                modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
            ){ innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.primary
                        )
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .verticalScroll(scrollState)
                ) {

                }
            }
        }
    }
    // endregion
    // endregion
    // endregion

    // endregion

    // region  --- Fields ---

    private val defaultButton: ButtonConfig by lazy { createButtonDefault() }

    // endregion

}