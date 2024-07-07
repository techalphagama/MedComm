package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments


data class AllComments(
    val allComments: List<CommentData>?,
)

data class CommentData(
    val commentId: String?,
    val commentContent: String?,
    val commentTime: String?,
    val commentedByUserId: String?,
    val commentedByUserName: String?,
    val likes: List<Like>?,
    val postId: String?,
    val replies: List<String?>?,
)

data class Like(
    val likeId: String?,
    val likedByUserId: String?,
    val likedByUserName: String?,
)