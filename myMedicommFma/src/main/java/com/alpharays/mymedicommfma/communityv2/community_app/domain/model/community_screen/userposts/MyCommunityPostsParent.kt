package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.userposts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MyCommunityPostsParent(
    @SerializedName("success") val success: Int? = null,
    @SerializedName("error") val error: String? = null,
    @SerializedName("errorlist") val errorList: List<String>? = null,
    @SerializedName("allMessages") val communityPostsData: List<MyCommunityPosts>? = null,
)