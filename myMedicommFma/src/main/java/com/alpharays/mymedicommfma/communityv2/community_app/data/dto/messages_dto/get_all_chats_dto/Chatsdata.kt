package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_all_chats_dto

data class Chatsdata(
    val __v: Int? = null,
    val createdAt: String? = null,
    val messages: List<Message?>? = listOf(),
    val room: String? = null,
)