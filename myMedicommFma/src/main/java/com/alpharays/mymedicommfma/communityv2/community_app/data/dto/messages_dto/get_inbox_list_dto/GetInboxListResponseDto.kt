package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_inbox_list_dto

data class GetInboxListResponseDto(
    val data: List<Data?>? = listOf(),
    val error: String? = null,
    val errorlist: List<String?>? = listOf(),
    val success: Int? = null,
)