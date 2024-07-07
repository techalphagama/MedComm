package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto

data class AllCommentsData(
    val __v: Int? = null,
    val _id: String? = null,
    val commentContent: String? = null,
    val commentTime: String? = null,
    val commentedByUserId: String? = null,
    val commentedByUserName: String? = null,
    val likes: List<Like?>? = listOf(),
    val postId: String? = null,
    val replies: List<String?> = listOf(),
)