package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.add_new_chat_dto

data class AddNewChatResponseDto(
    val data: Data? = Data(),
    val error: String? = null,
    val errorlist: List<String?>? = listOf(),
    val success: Int? = null,
)