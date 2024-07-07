package com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase

import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.AllRepliesResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.GetAllRepliesData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdateCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdatedCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.AllCommunityPostsData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.AllComments
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.PostReactionResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.ReactionBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.CommunityApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommunityUseCase @Inject constructor(
    private val communityApi: CommunityApi,
) {
    operator fun invoke(token: String): Flow<ResponseResult<AllCommunityPostsData?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val allPostsList = communityApi.getAllPostsList(token)
            emit(ResponseResult.Success(allPostsList.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }

    operator fun invoke(
        token: String,
        addNewCommunityPost: AddNewCommunityPost,
    ): Flow<ResponseResult<NewPostResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val response = communityApi.addNewPost(token, addNewCommunityPost)
            emit(ResponseResult.Success(response.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }

    operator fun invoke(
        token: String,
        postId: AllCommentsRequestBody,
    ): Flow<ResponseResult<AllComments?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val response = communityApi.getAllComments(postId, token)
            emit(ResponseResult.Success(response.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }

    operator fun invoke(
        token: String,
        addCommentData: AddCommentData,
    ): Flow<ResponseResult<AddCommentResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val response = communityApi.addNewComment(addCommentData, token)
            emit(ResponseResult.Success(response.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }

    operator fun invoke(
        token: String,
        reply: UpdateCommentData,
    ): Flow<ResponseResult<UpdatedCommentResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val response = communityApi.updateComment(reply, token)
            emit(ResponseResult.Success(response.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(e.message))
        }
    }

    operator fun invoke(
        token: String,
        commentId: String,
    ): Flow<ResponseResult<AllRepliesResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val response = communityApi.getAllReplies(GetAllRepliesData(commentId), token)
            emit(ResponseResult.Success(response.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(e.message))
        }
    }

    operator fun invoke(
        token: String,
        reactionBody: ReactionBody,
    ): Flow<ResponseResult<PostReactionResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            println("like_Testing : $token")
            println("like_Testing : $reactionBody")
            val response = communityApi.updatePostReaction(token, reactionBody)
            emit(ResponseResult.Success(response.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(e.message))
        }
    }
}