package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.all_comm_posts_dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Reactions(
    @SerialName("funny")
    val funny: List<ReactionType?>? = listOf(),
    @SerialName("celebrate")
    val celebrate: List<ReactionType?>? = listOf(),
    @SerialName("insightful")
    val insightful: List<ReactionType?>? = listOf(),
    @SerialName("like")
    val like: List<ReactionType?>? = listOf(),
    @SerialName("love")
    val love: List<ReactionType?>? = listOf(),
    @SerialName("support")
    val support: List<ReactionType?>? = listOf(),
    @SerialName("supportive")
    val supportive: List<ReactionType?>? = listOf(), // TODO: not need but still receiving in the response
)