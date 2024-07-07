package com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.sidedrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.share_app.ShareAppsBottomSheet
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary100
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing


data class NavDrawItem(
    val itemName: String,
    val itemId: Int,
    val itemRoute: String,
)

@Composable
fun ModalNavigationDrawerScreen(
    isLoggedIn: Boolean,
    navController: NavController,
    onSign: () -> Unit,
    onSignOut: () -> Unit,
) {
    val userName = "Shivang Gautam"
    val style = TextStyle(
        fontSize = MaterialTheme.typography.titleSmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W600,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
    )
    val style1 = TextStyle(
        fontWeight = FontWeight.W600,
        fontFamily = manRopeFontFamily,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
    )
    val isAuthorized by remember { mutableStateOf(false) }
    var openShareAppBottomSheet by remember { mutableStateOf(false) }
    val defaultRoute = CommunityAppScreens.CommunityScreen.route
    val noRoute = ""
    val shareRoute = "ShareRoute"
    val context = LocalContext.current
    val navigationDrawerItems = remember {
        mutableStateListOf(
            NavDrawItem("Settings", R.drawable.settings, noRoute),
            NavDrawItem("Rate us", R.drawable.rate_us, defaultRoute),
            NavDrawItem("Share With Friends", R.drawable.share_with_friends, shareRoute),
        )
    }
    val uriHandler = LocalUriHandler.current

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.7f)
            .background(Primary100)
            .padding(MaterialTheme.spacing.avgMedium)
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.avgMedium)
                .fillMaxHeight(),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.spacing.extraSmall,
                            bottom = MaterialTheme.spacing.medium
                        )
                        .clickable { },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(MaterialTheme.size.extraLarge)
                            .padding(MaterialTheme.spacing.extraSmall)
                            .clip(RoundedCornerShape(MaterialTheme.size.extraLarge)),
                        painter = painterResource(id = R.drawable.profile_image),
                        contentDescription = "profile pic",
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                vertical = MaterialTheme.spacing.extraSmall,
                                horizontal = MaterialTheme.spacing.medium,
                            ),
                        text = userName,
                        style = style
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = MaterialTheme.spacing.small)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxHeight(0.7f)
                ) {
                    itemsIndexed(navigationDrawerItems) { _, item ->
                        NavigationDrawerItemRow(
                            style = style1,
                            item = item.itemName,
                            painterId = item.itemId
                        ) {
                            MedCommToast.showToast(context, "Coming soon...")

                        }
                    }
                }
            }

            if (openShareAppBottomSheet) {
                ShareAppsBottomSheet(sharingMessage = "Share with friends") {
                    openShareAppBottomSheet = false
                }
            }
        }
    }
}

@Composable
fun NavigationDrawerItemRow(
    style: TextStyle,
    item: String,
    painterId: Int,
    onItemClicked: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.extraSmall,
                vertical = MaterialTheme.spacing.medSmall
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onItemClicked()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(MaterialTheme.size.defaultIconSize),
            painter = painterResource(painterId),
            contentDescription = item,
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier.padding(start = MaterialTheme.spacing.medSmall),
            text = item,
            style = style
        )
    }
}