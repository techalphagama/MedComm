package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.all_comm_posts_dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReactionType(
    @SerialName("name")
    val name: String? = null,
    @SerialName("reaction")
    val reaction: String? = null,
    @SerialName("reactionId")
    val reactionId: String? = null,
)