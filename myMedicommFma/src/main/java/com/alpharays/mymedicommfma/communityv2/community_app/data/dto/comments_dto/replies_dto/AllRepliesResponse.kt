package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto


import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsData

data class AllRepliesResponse(
    val data: List<AllCommentsData?>? = null,
    val error: String? = null,
    val errorList: List<String>? = null,
    val success: String? = null,
)