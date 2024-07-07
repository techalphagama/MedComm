package com.alpharays.mymedicommfma.communityv2.community_app.domain.repository

import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.AllCommunityPostsData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.AllComments
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.AllRepliesResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.GetAllRepliesData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdateCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdatedCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.PostReactionResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.ReactionBody

interface CommunityApi {
    suspend fun getAllPostsList(token: String): ResponseResult<AllCommunityPostsData>
//    suspend fun getCurrentUserPostsList(docId: String): ResponseResult<MyCommunityPostsParent>
    suspend fun addNewPost(token: String, addNewCommunityPost: AddNewCommunityPost): ResponseResult<NewPostResponse>
    suspend fun getAllComments(allCommentsRequestBody: AllCommentsRequestBody, token: String): ResponseResult<AllComments>
    suspend fun getAllReplies(commentId: GetAllRepliesData, token: String): ResponseResult<AllRepliesResponse>
    suspend fun addNewComment(addCommentData: AddCommentData, token: String): ResponseResult<AddCommentResponse>
    suspend fun updateComment(reply: UpdateCommentData, token: String): ResponseResult<UpdatedCommentResponse>
    suspend fun updatePostReaction(token: String, reactionBody: ReactionBody) : ResponseResult<PostReactionResponse>
}