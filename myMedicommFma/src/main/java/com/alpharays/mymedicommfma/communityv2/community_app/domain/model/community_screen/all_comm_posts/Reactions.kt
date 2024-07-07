package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts

data class Reactions(
    val like: List<ReactionType?>? = listOf(),
    val love: List<ReactionType?>? = listOf(),
    val celebrate: List<ReactionType?>? = listOf(),
    val support: List<ReactionType?>? = listOf(),
    val insightful: List<ReactionType?>? = listOf(),
    val funny: List<ReactionType?>? = listOf(),
)