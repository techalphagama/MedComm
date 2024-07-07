package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentData(
    @SerializedName("comment") val comment: String? = null,
    @SerializedName("_id") val postId: String? = null
)