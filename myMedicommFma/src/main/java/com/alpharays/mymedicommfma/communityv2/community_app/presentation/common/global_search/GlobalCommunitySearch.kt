package com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.global_search

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMUNITY_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BluishGray
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary200
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.fontSize
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing

@Composable
fun GlobalCommunitySearch(navController: NavController) {
//    var communitySearchText by remember { mutableStateOf("") }
//    var searchBarActive by remember { mutableStateOf(false) }
//
//     history items will be stored in ROOM DB, no need to store remotely
//    val communityHistoryItems = remember { mutableStateListOf<String>() }
//
//    val infiniteTransition = rememberInfiniteTransition(label = "twinkling_icon")
//    val alpha by infiniteTransition.animateFloat(
//        initialValue = 1f,
//        targetValue = 0f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(
//                durationMillis = 1500,
//                easing = LinearEasing
//            ),
//            repeatMode = RepeatMode.Reverse
//        ), label = "twinkling_icon"
//    )
//    val style = TextStyle(
//        fontSize = MaterialTheme.fontSize.medSmall,
//        fontFamily = manRopeFontFamily,
//        fontWeight = FontWeight.W500,
//        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
//    )
//    var searchBarHeight by remember { mutableIntStateOf(0) }
//
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 1f))
            .padding(MaterialTheme.spacing.small),
        verticalAlignment = Alignment.Top
    ) {
        GlobalSearchBox(navController)

//        SearchBar(
//            modifier = Modifier
//                .padding(4.dp)
//                .weight(1f),
//            shape = RoundedCornerShape(6.dp),
//            colors = SearchBarDefaults.colors(
//                containerColor = Primary200,
//                inputFieldColors = TextFieldDefaults.colors(
//                    focusedTextColor = FocusedTextColor,
//                    disabledTextColor = FocusedTextColor.copy(alpha = .3f),
//                    focusedIndicatorColor = BluishGray,
//                    unfocusedIndicatorColor = BluishGray,
//                    disabledIndicatorColor = BluishGray
//                )
//            ),
//            query = communitySearchText,
//            onQueryChange = {
//                communitySearchText = it
//            },
//            windowInsets = WindowInsets.ime,
//            onSearch = {
//                communityHistoryItems.add(communitySearchText)
//                searchBarActive = false
//            },
//            leadingIcon = {
//                Icon(
//                    modifier = Modifier.clickable {
//                        navController.navigate("community_screen"){
//                            popUpTo(0)
//                        }
//                    },
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "go back icon",
//                    tint = BluishGray
//                )
//            },
//            active = searchBarActive,
//            onActiveChange = {
//                searchBarActive = it
//            },
//            placeholder = {
//                Row(
//                    modifier = Modifier.onSizeChanged { searchBarHeight = it.height },
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    if (!searchBarActive) {
//                        VerticalDivider(
//                            modifier = Modifier
//                                .width(1.dp)
//                                .heightIn(
//                                    MaterialTheme.size.default,
//                                    with(LocalDensity.current) { searchBarHeight.toDp() }
//                                )
//                                .alpha(alpha),
//                            color = BluishGray
//                        )
//                    }
//                    Text(
//                        modifier = Modifier.padding(start = 5.dp),
//                        text = "What are you looking for ?",
//                        style = style
//                    )
//                }
//            },
//            trailingIcon = {
//                if (searchBarActive) {
//                    Icon(
//                        modifier = Modifier.clickable {
//                            if (communitySearchText.isNotEmpty()) {
//                                communitySearchText = ""
//                            } else {
//                                searchBarActive = false
//                            }
//                        },
//                        imageVector = Icons.Default.Close,
//                        contentDescription = "Close Icon",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                } else {
//                    Icon(
//                        modifier = Modifier.size(24.dp),
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "Search Icon",
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//            }
//        ) {
//            communityHistoryItems.forEach {
//                Row(
//                    modifier = Modifier.padding(12.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start
//                ) {
//                    Icon(
//                        modifier = Modifier.padding(8.dp),
//                        imageVector = Icons.Default.History,
//                        contentDescription = "History Icon"
//                    )
//                    Text(text = it)
//                }
//            }
//        }
    }
}

@Composable
fun GlobalSearchBox(navController: NavController) {
    var communitySearchText by remember { mutableStateOf("") }
    var searchBarActive by remember { mutableStateOf(false) }
    // history items will be stored in ROOM DB, no need to store remotely
    val communityHistoryItems = remember { mutableStateListOf<String>() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val infiniteTransition = rememberInfiniteTransition(label = "twinkling_icon")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = "twinkling_icon"
    )
    val style = TextStyle(
        fontSize = MaterialTheme.fontSize.medSmall,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
    )
    var searchBarHeight by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Primary200),
        shape = RoundedCornerShape(MaterialTheme.spacing.extraSmall),
        border = BorderStroke(MaterialTheme.spacing.default, Primary200)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.avgSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(COMMUNITY_SCREEN_ROUTE) {
                                popUpTo(0)
                            }
                        }
                        .padding(end = MaterialTheme.spacing.small)
                        .onSizeChanged { searchBarHeight = it.height },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "go back icon",
                    tint = BluishGray
                )

                if (!searchBarActive) {
                    VerticalDivider(
                        modifier = Modifier
                            .width(1.dp)
                            .heightIn(
                                MaterialTheme.size.default,
                                with(LocalDensity.current) { searchBarHeight.toDp() - MaterialTheme.spacing.extraSmall }
                            )
                            .alpha(alpha),
                        color = BluishGray
                    )
                }

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding()
                        .onFocusChanged {
                            searchBarActive = it.hasFocus
                        },
                    value = communitySearchText,
                    onValueChange = { newText ->
                        communitySearchText = newText
                    },
//                    placeholder = {
//                        Text(
//                            text = "What are you looking for ?",
//                            style = style
//                        )
//                    },
                    textStyle = style,
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = BluishGray,
//                        unfocusedBorderColor = Primary200,
//                    ),
                    keyboardActions = KeyboardActions(onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }),
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (searchBarActive) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (communitySearchText.isNotEmpty()) {
                                communitySearchText = ""
                            } else {
                                searchBarActive = false
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}