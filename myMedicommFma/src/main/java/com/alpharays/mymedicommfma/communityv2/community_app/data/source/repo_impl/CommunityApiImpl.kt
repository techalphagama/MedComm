package com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl


import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.alaskagemsdk.network.ResponseHandler
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.AllRepliesResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.GetAllRepliesData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdateCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdatedCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote.CommunityApiServices
import com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers.toAllCommentsDtoMapper
import com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers.toAllPostsDtoMapper
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.AllCommunityPostsData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.AllComments
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.PostReactionResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.ReactionBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.CommunityApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CommunityApiImpl @Inject constructor(
    private val apiServices: CommunityApiServices,
    private val responseHandler: ResponseHandler,
) : CommunityApi {
    override suspend fun getAllPostsList(token: String): ResponseResult<AllCommunityPostsData> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        Response.success(apiServices.getAllCommunityPosts(token).body()?.toAllPostsDtoMapper())
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

//    override suspend fun getCurrentUserPostsList(docId: String): ResponseResult<MyCommunityPostsParent> =
//        withContext(Dispatchers.IO) {
//            return@withContext try {
//                responseHandler.callAPI {
//                    withContext(Dispatchers.IO) {
//                        apiServices.getMyAllPosts(docId)
//                    }
//                }
//            } catch (e: Exception) {
//                ResponseResult.Error(SOMETHING_WENT_WRONG)
//            }
//        }

    override suspend fun addNewPost(
        token: String,
        addNewCommunityPost: AddNewCommunityPost,
    ): ResponseResult<NewPostResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            responseHandler.callAPI {
                withContext(Dispatchers.IO) {
                    apiServices.addMyNewPost(addNewCommunityPost, token)
                }
            }
        } catch (e: Exception) {
            ResponseResult.Error(SOMETHING_WENT_WRONG)
        }
    }

    override suspend fun getAllComments(
        allCommentsRequestBody: AllCommentsRequestBody,
        token: String,
    ): ResponseResult<AllComments> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    Response.success(apiServices.getAllComments(allCommentsRequestBody, token).body()?.toAllCommentsDtoMapper())
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllReplies(
        commentId: GetAllRepliesData,
        token: String,
    ): ResponseResult<AllRepliesResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.getAllReplies(token, commentId)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun addNewComment(
        addCommentData: AddCommentData,
        token: String,
    ): ResponseResult<AddCommentResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.addNewComment(addCommentData, token)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun updateComment(
        reply: UpdateCommentData,
        token: String,
    ): ResponseResult<UpdatedCommentResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.updateComment(reply, token)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun updatePostReaction(
        token: String,
        reactionBody: ReactionBody
    ) : ResponseResult<PostReactionResponse> =
        withContext(Dispatchers.IO){
            return@withContext try {
                responseHandler.callAPI {
                    apiServices.updatePostReaction(token, reactionBody)
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }
}