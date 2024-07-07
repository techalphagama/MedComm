package com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote

import com.alpharays.mymedicommfma.communityv2.MedCommRouter.TOKEN_KEYWORD
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_COMMENT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_NEW_CHAT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ADD_NEW_POST
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.ALL_COMMUNITY_POSTS
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_ALL_CHATS
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_ALL_COMMENTS
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_ALL_REPLIES_ON_COMMENT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.GET_INBOX_MESSAGES
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.REACT_TO_POST
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.UPDATE_COMMENT
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.all_comm_posts_dto.AllCommunityPostsDto
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.AllRepliesResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.replies_dto.GetAllRepliesData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdateCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdatedCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.add_new_chat_dto.AddNewChatResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_all_chats_dto.GetAllChatsResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_inbox_list_dto.GetInboxListResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.PostReactionResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.ReactionBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface CommunityApiServices {
    /**
     * community screen endpoints
     */

    // all community posts
    @GET(ALL_COMMUNITY_POSTS)
    suspend fun getAllCommunityPosts(
        @Header(TOKEN_KEYWORD) token: String,
    ): Response<AllCommunityPostsDto>

    // all new post
    @POST(ADD_NEW_POST)
    suspend fun addMyNewPost(
        @Body addNewCommunityPost: AddNewCommunityPost,
        @Header(TOKEN_KEYWORD) token: String,
    ): Response<NewPostResponse>

    // all comments
    @PUT(GET_ALL_COMMENTS)
    suspend fun getAllComments(
        @Body postIdBody: AllCommentsRequestBody,
        @Header(TOKEN_KEYWORD) token: String,
    ): Response<AllCommentsResponseDto>

    // all replies on a comment
    @POST(GET_ALL_REPLIES_ON_COMMENT)
    suspend fun getAllReplies(
        @Header(TOKEN_KEYWORD) token: String,
        @Body commentId: GetAllRepliesData,
    ): Response<AllRepliesResponse>

    // add new comment
    @POST(ADD_COMMENT)
    suspend fun addNewComment(
        @Body addCommentData: AddCommentData,
        @Header(TOKEN_KEYWORD) token: String,
    ): Response<AddCommentResponse>

    // reply on a comment
    @PUT(UPDATE_COMMENT)
    suspend fun updateComment(
        @Body reply: UpdateCommentData,
        @Header(TOKEN_KEYWORD) token: String,
    ): Response<UpdatedCommentResponse>

    // react to a post
    @POST(REACT_TO_POST)
    suspend fun updatePostReaction(
        @Header(TOKEN_KEYWORD) token: String,
        @Body reactionBody : ReactionBody,
    ) : Response<PostReactionResponse>


    /**
     * message screen endpoints
     */

    // add new chat
    @POST(ADD_NEW_CHAT)
    suspend fun startNewChat(
        @Header(TOKEN_KEYWORD) token: String,
        @Header("sid") receiverId: String,
    ): Response<AddNewChatResponseDto>

    // get all inbox messages
    @GET(GET_INBOX_MESSAGES)
    suspend fun getAllInboxMessagesList(
        @Header(TOKEN_KEYWORD) token: String,
    ): Response<GetInboxListResponseDto>

    // get all chats
    @GET(GET_ALL_CHATS)
    suspend fun getAllMessagesList(
        @Header(TOKEN_KEYWORD) token: String,
        @Header("chat_id") chatId: String,
    ): Response<GetAllChatsResponseDto>
}