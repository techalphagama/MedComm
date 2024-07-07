package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats

data class GetAllChatsResponse(
    val chatsData: List<ChatsResponse?>? = listOf(),
    val senderCheckId: String? = null,
)

data class ChatsResponse(
    val createdAt: String? = null,
    val messages: List<MessageResponse?>? = listOf(),
    val room: String? = null,
)

data class MessageResponse(
    val messageId: String? = null,
    val messageContent: String? = null,
    val senderId: String? = null,
    val sentTime: String? = null,
)