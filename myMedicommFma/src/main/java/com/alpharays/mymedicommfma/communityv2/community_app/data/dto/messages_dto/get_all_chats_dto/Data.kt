package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_all_chats_dto

data class Data(
    val chatsdata: List<Chatsdata?>? = listOf(),
    val id: String? = null, // this ID checks whether the current user has sent a message or received a message
)