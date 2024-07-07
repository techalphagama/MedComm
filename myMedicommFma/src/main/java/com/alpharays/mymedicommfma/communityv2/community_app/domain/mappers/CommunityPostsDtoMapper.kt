package com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers

import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.all_comm_posts_dto.AllCommunityPostsDto
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.AllCommunityPostsData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.ReactionType
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.Reactions

fun AllCommunityPostsDto.toAllPostsDtoMapper(): AllCommunityPostsData? {
    val response = this.data
    return try {
        AllCommunityPostsData(
            allPosts = response?.map {
                CommunityPost(
                    comments = it?.comments,
                    postId = it?._id,
                    myReaction = ReactionType(
                        name = it?.myreaction?.name,
                        reaction = it?.myreaction?.reaction,
                        reactionId = it?.myreaction?.reactionId,
                    ),
                    postContent = it?.postContent,
                    postTitle = it?.postTitle,
                    posterId = it?.posterId,
                    posterName = it?.posterName,
                    reactions = Reactions(
                        like = it?.reactions?.like?.map { reactionType ->
                            ReactionType(
                                name = reactionType?.name,
                                reaction = reactionType?.reaction,
                                reactionId = reactionType?.reactionId,
                            )
                        },
                        love = it?.reactions?.love?.map { reactionType ->
                            ReactionType(
                                name = reactionType?.name,
                                reaction = reactionType?.reaction,
                                reactionId = reactionType?.reactionId,
                            )
                        },
                        support = it?.reactions?.support?.map { reactionType ->
                            ReactionType(
                                name = reactionType?.name,
                                reaction = reactionType?.reaction,
                                reactionId = reactionType?.reactionId,
                            )
                        },
                        insightful = it?.reactions?.insightful?.map { reactionType ->
                            ReactionType(
                                name = reactionType?.name,
                                reaction = reactionType?.reaction,
                                reactionId = reactionType?.reactionId,
                            )
                        },
                        celebrate = it?.reactions?.celebrate?.map { reactionType ->
                            ReactionType(
                                name = reactionType?.name,
                                reaction = reactionType?.reaction,
                                reactionId = reactionType?.reactionId,
                            )
                        },
                        funny = it?.reactions?.funny?.map { reactionType ->
                            ReactionType(
                                name = reactionType?.name,
                                reaction = reactionType?.reaction,
                                reactionId = reactionType?.reactionId,
                            )
                        },
                    ),
                    aboutDoc = it?.aboutDoc,
                    doctorAvatar = it?.avatar
                )
            }
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}