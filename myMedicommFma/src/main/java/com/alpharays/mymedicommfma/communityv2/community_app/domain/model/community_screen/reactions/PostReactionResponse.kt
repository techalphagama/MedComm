package com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions

data class PostReactionResponse(
    val success: Int? = null,
    val error: String? = null,
    val errorlist: List<String>? = null,
    val data: String? = null,
)