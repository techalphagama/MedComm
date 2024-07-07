package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.ReactionType
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.Reactions
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CommunityPostDeserializer : JsonDeserializer<CommunityPost> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): CommunityPost {
        val jsonObject = json.asJsonObject

        val postId = jsonObject.get("postId")?.asString
        val aboutDoc = jsonObject.get("aboutDoc")?.asString
        val doctorAvatar = jsonObject.get("doctorAvatar")?.asString
        val postContent = jsonObject.get("postContent")?.asString
        val postTitle = jsonObject.get("postTitle")?.asString
        val posterId = jsonObject.get("posterId")?.asString
        val posterName = jsonObject.get("posterName")?.asString

        val commentsType = object : TypeToken<List<String>>() {}.type
        val comments = context.deserialize<List<String>>(jsonObject.get("comments"), commentsType) ?: listOf()

        val reactionsType = object : TypeToken<Reactions>() {}.type
        val reactions = context.deserialize<Reactions>(jsonObject.get("reactions"), reactionsType) ?: Reactions()

        val myReactionType = object : TypeToken<ReactionType>() {}.type
        val myReaction = context.deserialize<ReactionType>(jsonObject.get("myReaction"), myReactionType)

        return CommunityPost(
            postId = postId,
            aboutDoc = aboutDoc,
            doctorAvatar = doctorAvatar,
            comments = comments,
            postContent = postContent,
            postTitle = postTitle,
            posterId = posterId,
            posterName = posterName,
            reactions = reactions,
            myReaction = myReaction
        )
    }
}