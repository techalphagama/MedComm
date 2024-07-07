package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto

data class AllCommentsResponseDto(
    val `data`: List<AllCommentsData?>? = listOf(),
    val error: Any? = null,
    val errorlist: List<Any?>? = listOf(),
    val success: Int? = null,
)