package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

object CommunityConstants {
    // community screen
    const val ALL_COMMUNITY_POSTS = "doc/alldocsposts"
    const val ADD_NEW_POST = "doc/addpost"
    const val REACT_TO_POST = "doc/reacttopost"
    const val ADD_COMMENT = "doc/addcomment"
    const val UPDATE_COMMENT = "doc/updatecomment"
    const val GET_ALL_REPLIES_ON_COMMENT = "doc/allreplies"
    const val GET_ALL_COMMENTS = "doc/allcomments"

    const val ADD_NEW_CHAT = "chats/addnewchat"
    const val GET_INBOX_MESSAGES = "chats/getinboxlist"
    const val GET_ALL_CHATS = "chats/getallchats"

    // navigation routes
    const val COMMUNITY_SCREEN_ROUTE = "community_screen"
    const val GLOBAL_COMMUNITY_SEARCH_SCREEN_ROUTE = "global_community_screen_route"
    const val DIRECT_MESSAGE_SCREEN_ROUTE = "direct_message_screen_route"
    const val MESSAGE_INBOX_SCREEN_ROUTE = "message_inbox_screen_route"
    const val ADD_NEW_POST_SCREEN_ROUTE = "add_new_community_post_screen_route"
    const val COMMENTS_SCREEN_ROUTE = "comments_screen_route"
    const val COMMUNITY_REPOST_SCREEN_ROUTE = "community_repost_screen_route"
    const val COMMENTS_REPLY_FULL_SCREEN_ROUTE = "comments_reply_full_screen_route"

    // TODO : community app not linked to room db -> only initialized
    const val MEDICO_COMMUNITY_TABLE = "medico_community_table"

    const val LIKE_POST_CD = "Like Post"
    const val LOVE_POST_CD = "Love Post"
    const val CELEBRATE_POST_CD = "Celebrate Post"
    const val SUPPORT_POST_CD = "ReactionType Post"
    const val INSIGHTFUL_POST_CD = "Insightful Post"
    const val FUNNY_POST_CD = "Funny Post"
    const val CAN_NOT_REACT_NO_CONNECTION = "Can not react, No connection"
    const val CAN_NOT_COMMENT_NO_CONNECTION = "Can not comment, No connection"
    const val CAN_NOT_REPOST_NO_CONNECTION = "Can not comment, No connection"
    const val CAN_NOT_SEND_NO_CONNECTION = "No connection, try again later"

    // socket events
    const val MESSAGE_REPLY_EVENT = "reply"
    const val SEND_MESSAGE = "sendmessage"
    const val JOIN_ROOM = "joinroom"
    const val MESSAGES_ROOM = "messageroom"

    const val LIKE_OPTION = "Like"
    const val LOVE_OPTION = "Love"
    const val CELEBRATE_OPTION = "Celebrate"
    const val SUPPORT_OPTION = "Support"
    const val INSIGHTFUL_OPTION = "Insightful"
    const val FUNNY_OPTION = "Funny"
    const val COMMENT_OPTION = "Comment"
    const val REPOST_OPTION = "Repost"
    const val SEND_OPTION = "Send"
    const val LIKE_PAINTER_CONTENT_DSC = "Like Image"
    const val COMMENT_PAINTER_CONTENT_DSC = "Comment Image"
    const val REPOST_PAINTER_CONTENT_DSC = "Repost Image"
    const val SEND_PAINTER_CONTENT_DSC = "Send Image"

    const val TOKEN = "token"
    const val POST_ID = "post_id"
    const val COMM_TAG_VM = "COMM_TAG_VM"
}