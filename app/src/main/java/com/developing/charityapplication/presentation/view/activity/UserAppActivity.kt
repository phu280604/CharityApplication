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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.developing.charityapplication.HeartBellApplication
import com.developing.charityapplication.presentation.view.component.button.ButtonConfig
import com.developing.charityapplication.presentation.view.component.button.builder.ButtonComponentBuilder
import com.developing.charityapplication.presentation.view.theme.HeartBellTheme
import com.developing.charityapplication.R
import com.developing.charityapplication.presentation.view.component.navItem.NavItemConfig
import com.developing.charityapplication.presentation.view.component.post.PostComponent
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography
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
    fun UserAppUI(){
        val scrollState = rememberScrollState()
        HeartBellTheme {
            Scaffold(
                topBar = { Header() },
                bottomBar = {
                    NavigationBar(
                        contentColor = AppColorTheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(40.dp, 80.dp)
                    ) {
                        var selectedItemIndex by remember { mutableIntStateOf(0) }
                        val navItem = footer()
                        navItem.forEachIndexed{
                            index, item ->

                            var color = if (item.title == 0) AppColorTheme.primary
                            else if(item.isSelected) AppColorTheme.secondary
                            else AppColorTheme.onPrimary

                            val modifier = if (item.title == 0) Modifier
                                .size(56.dp)
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
                                        colorFilter = if(!item.skipOption)
                                            ColorFilter.tint(color)
                                        else null,
                                        modifier = modifier
                                    )
                                },
                                label = {
                                    if(item.title != 0)
                                        Text(
                                            text = stringResource(id = item.title) ,
                                            style = AppTypography.bodyMedium
                                        )
                                },
                                selected = selectedItemIndex == index,
                                onClick = {
                                    navItem[selectedItemIndex].isSelected = false
                                    selectedItemIndex = index
                                    item.isSelected = true
                                    /*TODO: Navigation Implement*/
                                },
                                colors = NavigationBarItemDefaults.colors().copy(
                                    selectedIndicatorColor = Color.Transparent,
                                    selectedTextColor = AppColorTheme.secondary
                                )
                            )
                        }
                    }
                },
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
                // endregion

                // region - Notification Button -
                val notificationButton = ButtonComponentBuilder()
                    .withConfig(
                        newConfig = defaultButton.copy(
                            onClick = { /*TODO: Implement notification logic*/ },
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
                ){
                    notificationButton.BaseDecorate {  }
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
    }


    @Composable
    fun footer(): List<NavItemConfig>{
        return remember {
            listOf(
                NavItemConfig(
                    title = R.string.nav_home,
                    icon = R.drawable.ic_nav_home,
                    isSelected = true,
                ),
                NavItemConfig(
                    title = R.string.nav_following,
                    icon = R.drawable.ic_nav_users,
                    isSelected = false,
                ),
                NavItemConfig(
                    icon = R.drawable.ic_nav_plus,
                    isSelected = false,
                ),
                NavItemConfig(
                    title = R.string.nav_chatting,
                    icon = R.drawable.ic_nav_chattting,
                    isSelected = false,
                    badgeCount = 0
                ),
                NavItemConfig(
                    title = R.string.nav_profile,
                    icon = R.drawable.avt_young_girl,
                    isSelected = false,
                    skipOption = true,
                )
            )
        }
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

    // region -- Main UI Overview --
    @Preview
    @Composable
    fun MainUIOverview(){
        UserAppUI()
    }
    // endregion
    // endregion
    // endregion

    // endregion

    // region  --- Fields ---

    private val defaultButton: ButtonConfig by lazy { createButtonDefault() }

    // endregion

}