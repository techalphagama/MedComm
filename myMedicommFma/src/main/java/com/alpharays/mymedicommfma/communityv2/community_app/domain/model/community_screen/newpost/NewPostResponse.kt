package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NewPostResponse(
    @SerializedName("allMessages") val newPostData: String? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    @SerializedName("success") val success: String? = null
)