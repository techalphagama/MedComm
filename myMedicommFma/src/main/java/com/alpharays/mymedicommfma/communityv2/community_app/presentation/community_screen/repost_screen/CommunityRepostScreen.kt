package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.repost_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.full_post_screen.PostComposableContent
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostSharedViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BluishGray
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.LessFocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.OnPrimaryFixed
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary400
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.TransparentColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.fontSize
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityRepostScreen(
    navController: NavController,
    viewModel: CommunityRepostViewModel = hiltViewModel(),
    postSharedViewModel: PostSharedViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        postSharedViewModel.getCurrentPostState()
    }
    val currentPost by postSharedViewModel.postContentState.collectAsStateWithLifecycle()
    val cardBorderBrush = Brush.linearGradient(colors = listOf(LessFocusedTextColor, OnPrimaryFixed, Color.Transparent, BluishGray, LessFocusedTextColor))
    var myRepostThoughts by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
//    val response = viewModel.addNewCommunityPost()
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CommunityRepostComposable(navController)
        },
        bottomBar = {
            RepostBottomBarComposable(viewModel = viewModel)
        },
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .95f)
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(MaterialTheme.spacing.extraSmall)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.spacing.extraSmall)
                ) {
                    TextField(
                        value = myRepostThoughts,
                        onValueChange = { newText ->
                            if(newText.trimStart().length >= 200){
                                MedCommToast.showToast(context, "Character Limit Exceeded...")
                            }
                            else{
                                myRepostThoughts = newText.trimStart()
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Share your thoughts...",
                                style = TextStyle(
                                    fontSize = MaterialTheme.fontSize.medium,
                                    fontWeight = FontWeight.W400,
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = .5f),
                                    fontFamily = manRopeFontFamily
                                )
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = TransparentColor,
                            unfocusedIndicatorColor = TransparentColor,
                            focusedContainerColor = TransparentColor,
                            unfocusedContainerColor = TransparentColor,
                        ),
                        textStyle = TextStyle(
                            fontSize = MaterialTheme.fontSize.large,
                            fontWeight = FontWeight.W500,
                            color = MaterialTheme.colorScheme.surface.copy(alpha = .87f),
                            fontFamily = workSansFontFamily
                        )
                    )
                }
            }
            item {
                PostComposableContent(
                    navController = navController,
                    communityViewModel = null,
                    postSharedViewModel = postSharedViewModel,
                    post = currentPost ?: CommunityPost(),
                    allCommentsData = null,
                    cardBorderBrush = cardBorderBrush
                )
            }
        }
    }
}

@Composable
fun CommunityRepostComposable(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Primary400),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.avgExtraSmall),
        shape = RoundedCornerShape(MaterialTheme.spacing.default)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.avgSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .clickable {
                        navController.navigate(CommunityAppScreens.CommunityScreen.route) {
                            popUpTo(0)
                        }
                    },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "close post"
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more options"
            )
        }
    }
}

@Composable
fun RepostBottomBarComposable(viewModel: CommunityRepostViewModel) {
    var repost by remember { mutableStateOf(false) }
    val style = TextStyle(
        textAlign = TextAlign.End,
        color = FocusedTextColor,
        fontWeight = FontWeight.W600,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        Text(
            text = "Repost",
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium)
                .clickable { repost = true },
            style = style
        )
    }

    val context = LocalContext.current
    if(repost){
        repost = false
        MedCommToast.showToast(context, "Coming soon...")
//        viewModel.addNewCommunityPost(AddNewCommunityPost(postTitle, postContent))
    }
}