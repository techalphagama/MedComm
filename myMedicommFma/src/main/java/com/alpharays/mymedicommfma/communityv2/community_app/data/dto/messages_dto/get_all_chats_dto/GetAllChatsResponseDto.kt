package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_all_chats_dto

data class GetAllChatsResponseDto(
    val data: Data? = Data(),
    val error: String? = null,
    val errorlist: List<String?>? = null,
    val success: Int? = null,
)