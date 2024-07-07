package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.add_new_chat

data class AddNewChatResponse(
    val chatId: String? = null,
    val createdAt: String? = null,
    val messages: List<String?>? = listOf(),
    val participants: List<String?>? = listOf(),
    val room: String? = null,
)
