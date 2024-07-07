package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts

data class CommunityPost(
    val comments: List<String?>? = listOf(),
    val postId: String? = null,
    val myReaction: ReactionType? = ReactionType(),
    val postContent: String? = null,
    val postTitle: String? = null,
    val aboutDoc: String? = null,
    val doctorAvatar: String? = null,
    val posterId: String? = null,
    val posterName: String? = null,
    val reactions: Reactions? = Reactions(),
)
