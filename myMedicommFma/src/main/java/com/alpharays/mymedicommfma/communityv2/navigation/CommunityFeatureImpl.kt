package com.alpharays.mymedicommfma.communityv2.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alpharays.mymedicommfma.communityv2.CommunityFeatureApi
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.global_search.GlobalCommunitySearch
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.CommunityScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.full_post_screen.CommunityFullPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.new_post_screen.AddNewCommunityPostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.reply_screen.CommentReplyFullScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.repost_screen.CommunityRepostScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message.DirectMessageScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox.MessageInboxScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens

private const val baseRoute = "community_screen"
private const val commRouteScenario = "$baseRoute/scenario"

class CommunityFeatureImpl : CommunityFeatureApi {
    override val communityRoute: String
        get() = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        //base screen for the community feature
        navGraphBuilder.composable(baseRoute) {
            CommunityScreen(navController = navController)
        }


        //define the navigation graph for the community feature
        navGraphBuilder.navigation(
            route = commRouteScenario,
            startDestination = CommunityAppScreens.AddNewCommunityPostScreen.route
        ) {
            composable(route = CommunityAppScreens.GlobalCommunitySearch.route) {
                GlobalCommunitySearch(navController = navController)
            }

            composable(route = CommunityAppScreens.AddNewCommunityPostScreen.route) {
                AddNewCommunityPostScreen(navController = navController)
            }

            composable(
                route = CommunityAppScreens.CommunityFullPostScreen.route + "/{currentPostId}" + "/{addComment}",
                arguments = listOf(
                    navArgument("currentPostId") {
                        type = NavType.StringType
                    },
                    navArgument("addComment") {
                        type = NavType.BoolType
                    }
                )
            ) { entry ->
                val context = LocalContext.current
                val postId = entry.arguments?.getString("currentPostId") ?: ""
                val addComment = entry.arguments?.getBoolean("addComment") ?: false
                CommunityUtils.setOneTimePostId(context, postId)
                CommunityFullPostScreen(navController = navController, addComment = addComment)
            }

            composable(route = CommunityAppScreens.CommunityRepostScreen.route) {
                CommunityRepostScreen(navController = navController)
            }

            composable(
                route = CommunityAppScreens.CommentReplyFullScreen.route + "/{currentPostId}" + "/{commentId}",
                arguments = listOf(
                    navArgument("currentPostId") {
                        type = NavType.StringType
                    },
                    navArgument("commentId") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val postId = entry.arguments?.getString("currentPostId") ?: ""
                val commentId = entry.arguments?.getString("commentId") ?: ""
                CommentReplyFullScreen(
                    navController = navController,
                    postId = postId,
                    commentId = commentId
                )
            }

            // message screen(s)
            composable(route = CommunityAppScreens.MessageInboxScreen.route) {
                MessageInboxScreen(navController = navController)
            }

            composable(
                route = CommunityAppScreens.DirectMessageScreen.route + "/{chatId}" + "/{chatUserName}" + "/{chatUserImage}",
                arguments = listOf(
                    navArgument("chatId") {
                        type = NavType.StringType
                    },
                    navArgument("chatUserName") {
                        type = NavType.StringType
                    },
                    navArgument("chatUserImage") {
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val chatId = entry.arguments?.getString("chatId") ?: ""
                val chatUserName = entry.arguments?.getString("chatUserName") ?: ""
                val chatUserImage = entry.arguments?.getString("chatUserImage") ?: ""
                DirectMessageScreen(
                    navController = navController,
                    chatId = chatId,
                    chatUserName = chatUserName,
                    chatUserImage = chatUserImage
                )
            }
        }
    }
}

