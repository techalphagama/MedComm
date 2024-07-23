package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddCommentResponse(
    @SerializedName("data") val data: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorlist: List<String>? = null,
    @SerializedName("success") val success: String? = null
)