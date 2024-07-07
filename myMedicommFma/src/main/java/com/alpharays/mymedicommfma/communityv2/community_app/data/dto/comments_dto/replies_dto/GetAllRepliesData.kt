package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetAllRepliesData(
    @SerializedName("commentId") val commentId: String? = null
)