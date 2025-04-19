@file:OptIn(ExperimentalMaterial3Api::class)

package com.developing.charityapplication.presentation.view.screen.user.follower

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.developing.charityapplication.R
import com.developing.charityapplication.infrastructure.utils.DefaultValue
import com.developing.charityapplication.presentation.view.component.post.PostConfig
import com.developing.charityapplication.presentation.view.theme.AppColorTheme
import com.developing.charityapplication.presentation.view.theme.AppTypography

// region --- Methods ---
@Composable
fun HeaderFollower(navController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp, 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.follower_page),
                    style = AppTypography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = AppColorTheme.secondary
                )
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_nav_users),
                    contentDescription = null,
                    tint = AppColorTheme.secondary,
                    modifier = Modifier.size(32.dp)
                )
            },
            actions = {
                IconButton(
                    onClick = { /*TODO: Implement search follower*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null,
                        tint = AppColorTheme.onPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppColorTheme.primary,
                titleContentColor = AppColorTheme.secondary.copy(alpha = 0.8f),
                navigationIconContentColor = AppColorTheme.secondary.copy(alpha = 0.8f),
            )
        )
    }
}

@Composable
fun FollowerPageScreen(){

    var selectedCateState = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AppColorTheme.primary)
    ) {
        FollowerCategory(selectedCateState, Modifier.weight(0.1f))

        UsersList(
            datas = emptyList(),
            selectedCate = selectedCateState.intValue,
            modifier = Modifier.weight(1f)
        )
    }
}

// region --- Body Section ---
@Composable
fun FollowerCategory(
    selectedCate: MutableIntState,
    modifier: Modifier
){
    val followerCate = listOf(
        stringResource(R.string.stranger_cate),
        stringResource(R.string.invitation_cate),
        stringResource(R.string.friend_cate)
    )

    LazyRow(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
    ) {
        itemsIndexed(followerCate){
            index, item ->
            val isSelected = selectedCate.intValue == index
            AssistChip(
                onClick = {
                    selectedCate.intValue = index
                    /*TODO: Implement chip follower category*/
                },
                label = {
                    Text(
                        text = item,
                        style = AppTypography.bodyMedium,
                        color = AppColorTheme.primary
                    )
                },
                shape = RoundedCornerShape(16.dp),
                colors = AssistChipDefaults.assistChipColors().copy(
                    containerColor = if (isSelected) AppColorTheme.secondary.copy(alpha = 0.9f) else AppColorTheme.onPrimary.copy(alpha = 0.4f),

                )
            )
        }
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = AppColorTheme.onPrimary.copy(alpha = 0.6f)
    )
}

@Composable
fun UsersList(
    datas: List<PostConfig>,
    selectedCate: Int,
    modifier: Modifier
){
    val sentStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
    ) {
        items(datas){
            user ->
            val isSent = sentStates[user.username] ?: false

            when(selectedCate){
                0 -> {
                    UserFollowItem(
                        data = user,
                        cateIndex = selectedCate,
                        isSent = isSent,
                        onAccepted = {
                            sentStates[user.username] = true
                            /*TODO: Implement accepted button*/
                        },
                        onDeclined = {
                            sentStates[user.username] = false
                            /*TODO: Implement decline button*/
                        }
                    )
                }
                1 -> {
                    UserFollowItem(
                        data = user,
                        cateIndex = selectedCate,
                        isSent = isSent,
                        onAccepted = {
                            sentStates[user.username] = true
                            /*TODO: Implement accepted button*/
                        },
                        onDeclined = {
                            sentStates[user.username] = false
                            /*TODO: Implement decline button*/
                        }
                    )
                }
                2 -> {
                    UserFollowItem(
                        data = user,
                        cateIndex = selectedCate,
                        isSent = isSent,
                        onAccepted = {
                            sentStates[user.username] = true
                            /*TODO: Implement accepted button*/
                        },
                        onDeclined = {
                            sentStates[user.username] = false
                            /*TODO: Implement decline button*/
                        }
                    )
                }
            }
        }
    }
}

// region --- User Follow Item ---
@Composable
fun UserFollowItem(
    data: PostConfig,
    cateIndex: Int,
    isSent: Boolean,
    onAccepted: () -> Unit,
    onDeclined: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        // region -- Avatar Section --
        Image(
            painter = data.userbackground ?: painterResource(id = DefaultValue.avatar),
            contentDescription = null,
            modifier = Modifier
                .weight(0.3f)
        )
        // endregion

        // region -- Interaction User Section --
        Column(
            modifier = Modifier
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically)
        ) {
            // region -- Username Section --
            Text(
                text = data.username,
                style = AppTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = AppColorTheme.onPrimary
            )
            // endregion

            // region -- Button Section -
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
            ) {
                // region - Accepted Button -
                if (cateIndex != 2 && !isSent) {
                    AssistChip(
                        onClick = {
                            onAccepted()
                        },
                        label = {
                            val buttonText = when(cateIndex){
                                0 -> {
                                    stringResource(id = R.string.friend_button)
                                }
                                else -> {
                                    stringResource(id = R.string.accept_button)
                                }
                            }
                            Text(
                                text = buttonText,
                                style = AppTypography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors().copy(
                            containerColor = AppColorTheme.secondary,
                            labelColor = AppColorTheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
                // endregion

                // region - Declined Button -
                if (isSent || cateIndex != 0){
                    AssistChip(
                        onClick = {
                            onDeclined()
                        },
                        label = {
                            val buttonText = when(cateIndex){
                                2 -> {
                                    stringResource(id = R.string.friend_decline_button)
                                }
                                else -> {
                                    stringResource(id = R.string.decline_button)
                                }
                            }
                            Text(
                                text = buttonText,
                                style = AppTypography.bodyMedium,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors().copy(
                            containerColor = AppColorTheme.onPrimary.copy(alpha = 0.4f),
                            labelColor = AppColorTheme.primary
                        ),
                        modifier = Modifier.weight(1f)
                    )
                }
                // endregion
            }
            // endregion
        }
        // endregion
    }
}
// endregion
// endregion

// endregion