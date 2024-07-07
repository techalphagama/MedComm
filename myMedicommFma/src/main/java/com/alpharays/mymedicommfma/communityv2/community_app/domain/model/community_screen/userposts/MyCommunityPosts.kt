package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.userposts

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.Reactions
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// img url
// time of posting
@Serializable
data class MyCommunityPosts(
    @SerializedName("_id") val id: String? = null,
    val posterId: String? = null,
    val posterName: String? = null,
    val postTitle: String? = null,
    val postContent: String? = null,
    val reactions: Reactions? = null, // TODO: added just for now
    val comments: List<String>? = null,
    @SerializedName("__v") val v: Int,
)