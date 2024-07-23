package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.reply_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommentsViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.full_post_screen.CommentsBottomBarComposable
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.full_post_screen.ComposableAllComments
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary400
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing

@Composable
fun CommentReplyFullScreen(
    navController: NavHostController,
    postId: String,
    commentId: String,
    commentsViewModel: CommentsViewModel = hiltViewModel(),
) {
    val response by commentsViewModel.allRepliesStateFlow.collectAsStateWithLifecycle()
    val allCommentsData = response.data?.data ?: emptyList()
//    LaunchedEffect(commentId) {
//        if(commentId.isNotEmpty()){
//            commentsViewModel.getAllReplies(commentId)
//        }
//    }
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CommentReplyTopBarComposable(navController, postId)
        },
        bottomBar = {
            CommentsBottomBarComposable(openCommentTextField = false, viewModel = commentsViewModel)
        },
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .95f)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = MaterialTheme.spacing.small,
                    vertical = MaterialTheme.spacing.extraSmall
                )
        ) {
            Row(modifier = Modifier.padding(bottom = MaterialTheme.spacing.medSmall)) {
                Text(text = "Replies", style = style)
            }

            ComposableAllComments(
                navController = navController,
                currentPostId = postId,
                allCommentsData = allCommentsData
            )
        }
    }
}

@Composable
fun CommentReplyTopBarComposable(navController: NavController, postId: String) {
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
                        navController.navigate(CommunityAppScreens.CommunityFullPostScreen.route + "/$postId" + "/false") {
                            // TODO: popUpTo & popBackStack not working
//                            popUpTo(0){
//                                inclusive = false
//                            }
                            navController.popBackStack()
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