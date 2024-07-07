package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCommentData(
    @SerializedName("commentId") val commentId: String? = null,
    @SerializedName("reply") val reply: String? = null
)