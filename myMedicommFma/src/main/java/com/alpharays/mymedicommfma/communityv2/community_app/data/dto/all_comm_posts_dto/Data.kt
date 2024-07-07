package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.all_comm_posts_dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("aboutDoc")
    val aboutDoc: String? = null,
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("comments")
    val comments: List<String?>? = listOf(),
    @SerialName("_id")
    val _id: String? = null,
    @SerialName("myreaction")
    val myreaction: ReactionType? = ReactionType(),
    @SerialName("postContent")
    val postContent: String? = null,
    @SerialName("postTitle")
    val postTitle: String? = null,
    @SerialName("posterId")
    val posterId: String? = null,
    @SerialName("posterName")
    val posterName: String? = null,
    @SerialName("reactions")
    val reactions: Reactions? = Reactions(),
    @SerialName("__v")
    val v: String? = null,
)
// TODO: serialization not working