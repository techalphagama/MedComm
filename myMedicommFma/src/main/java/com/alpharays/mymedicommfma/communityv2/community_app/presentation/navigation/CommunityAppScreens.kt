package com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation

import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_NEW_POST_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENTS_REPLY_FULL_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENTS_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMUNITY_REPOST_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMUNITY_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.DIRECT_MESSAGE_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GLOBAL_COMMUNITY_SEARCH_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.MESSAGE_INBOX_SCREEN_ROUTE

sealed class CommunityAppScreens(val route: String) {
     data object CommunityScreen : CommunityAppScreens(COMMUNITY_SCREEN_ROUTE)
     data object GlobalCommunitySearch : CommunityAppScreens(GLOBAL_COMMUNITY_SEARCH_SCREEN_ROUTE)
     data object AddNewCommunityPostScreen : CommunityAppScreens(ADD_NEW_POST_SCREEN_ROUTE)
     data object CommunityFullPostScreen : CommunityAppScreens(COMMENTS_SCREEN_ROUTE)
     data object CommunityRepostScreen : CommunityAppScreens(COMMUNITY_REPOST_SCREEN_ROUTE)
     data object CommentReplyFullScreen : CommunityAppScreens(COMMENTS_REPLY_FULL_SCREEN_ROUTE)
     data object DirectMessageScreen : CommunityAppScreens(DIRECT_MESSAGE_SCREEN_ROUTE)
     data object MessageInboxScreen : CommunityAppScreens(MESSAGE_INBOX_SCREEN_ROUTE)
}