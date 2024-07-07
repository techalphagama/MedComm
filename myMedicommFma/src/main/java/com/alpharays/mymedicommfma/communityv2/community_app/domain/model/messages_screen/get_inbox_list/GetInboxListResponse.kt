package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list

data class GetInboxListResponse(
    val allMessages: List<InboxResponse?>? = listOf(),
)

data class InboxResponse(
    val chatId: String? = null,
    val userImage: String? = null,
    val userName: String? = null,
    val createdAt: String? = null,
    val lastMessage: String? = null,
    val lastMsgTimeStamp: String? = null,
    val room: String? = null,
)