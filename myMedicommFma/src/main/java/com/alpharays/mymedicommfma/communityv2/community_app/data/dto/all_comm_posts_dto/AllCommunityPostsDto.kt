package com.alpharays.mymedicommfma.communityv2.community_app.data.dto.all_comm_posts_dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllCommunityPostsDto(
    @SerialName("allMessages")
    val data: List<Data?>? = listOf(),
    @SerialName("error")
    val error: String? = null,
    @SerialName("errorlist")
    val errorlist: List<String?>? = listOf(),
    @SerialName("success")
    val success: Int? = null,
)