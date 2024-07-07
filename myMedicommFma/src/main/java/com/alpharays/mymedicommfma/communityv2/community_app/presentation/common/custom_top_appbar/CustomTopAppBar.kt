package com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.custom_top_appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.share_app.ShareAppsBottomSheet
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary400
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary700
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.fontSize
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    drawerState: DrawerState,
    showLogo: Boolean = true,
    navController: NavController,
) {
    val burgerMenuIcon = R.drawable.burger_menu_icon
    val burgerMenuIconDesc = "open navigation drawer"

    val searchIcon = R.drawable.search_icon
    val searchIconDesc = "global search"

    val chatIcon = R.drawable.messages
    val chatIconDesc = "open message block"

    val shareIcon = R.drawable.share_with_friends
    val shareIconDesc = "share the app with friends"

    val scope = rememberCoroutineScope()
    var initiateShare by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier,
        title = { if (showLogo) TopBarAppName() },
        navigationIcon = {
            TopAppBarIcon(
                iconId = burgerMenuIcon,
                contentDescription = burgerMenuIconDesc,
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                topBarIcon = TopBarIcons.DrawerIcon
            )
        },
        actions = {
            LazyRow{
                items(TopBarIcons.entries){ topBarIcon ->
                    if(topBarIcon != TopBarIcons.DrawerIcon){
                        val iconId: Int
                        val iconContentDescription: String
                        val iconRoute: String
                        when(topBarIcon){
                            TopBarIcons.SearchIcon -> {
                                iconId = searchIcon
                                iconContentDescription = searchIconDesc
                                iconRoute = CommunityAppScreens.GlobalCommunitySearch.route
                            }

                            TopBarIcons.MessageIcon -> {
                                iconId = chatIcon
                                iconContentDescription = chatIconDesc
                                iconRoute = CommunityAppScreens.MessageInboxScreen.route
                            }

                            else -> {
                                iconId = shareIcon
                                iconContentDescription = shareIconDesc
                                iconRoute = shareIconDesc
                            }
                        }

                        TopAppBarIcon(
                            topBarIcon = topBarIcon,
                            iconId = iconId,
                            contentDescription = iconContentDescription,
                            onClick = {
                                if(iconRoute == shareIconDesc){
                                    initiateShare =  true
                                }
                                else{
                                    navController.navigate(iconRoute)
                                }
                            }
                        )
                    }
                }
            }
//            TopAppBarIcon(
//                iconId = chatIcon,
//                contentDescription = chatIconDesc,
//                onClick = {}
//            )
//            TopAppBarIcon(
//                iconId = shareIcon,
//                contentDescription = shareIconDesc,
//                onClick = { initiateShare = true }
//            )
        },
        colors = TopAppBarDefaults.topAppBarColors(Primary400)
    )

    if (initiateShare) {
        ShareAppsBottomSheet("Share App With Friends") {
            initiateShare = false
        }
    }
}

enum class TopBarIcons {
    DrawerIcon, SearchIcon, MessageIcon, ShareIcon
}

@Composable
private fun TopBarAppName() {
    val appName = stringResource(id = R.string.app_name)
    val fontSize = MaterialTheme.fontSize.medLarge

    val style = TextStyle(
        fontSize = fontSize,
        fontWeight = FontWeight.Normal,
        color = MaterialTheme.colorScheme.surface,
        fontFamily = workSansFontFamily
    )

    Text(text = appName, style = style)
}

@Composable
private fun TopAppBarIcon(
    @DrawableRes iconId: Int,
    contentDescription: String,
    topBarIcon: TopBarIcons,
    onClick: () -> Unit,
) {
    val painter = painterResource(id = iconId)
    val iconSize = MaterialTheme.size.defaultIconSize
    val colorFilter = if(topBarIcon == TopBarIcons.MessageIcon) null else ColorFilter.tint(color = Primary700)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.small)
            .size(iconSize)
            .clickable { onClick() },
        colorFilter = colorFilter
    )
}