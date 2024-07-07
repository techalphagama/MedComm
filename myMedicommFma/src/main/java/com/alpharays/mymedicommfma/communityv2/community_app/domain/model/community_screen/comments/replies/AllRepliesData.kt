package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.replies

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.CommentData

data class AllRepliesData(
    val data: List<CommentData?>? = listOf(),
)
