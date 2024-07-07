package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.UNEXPECTED_ERROR
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.add_dto.AddCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsRequestBody
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdateCommentData
import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.update_dto.UpdatedCommentResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers.formattedCommentTime
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.CommentData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.Like
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.replies.AllRepliesData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    context: Context,
    private val networkObserver: NetworkConnectivityObserver,
    private val communityUseCase: CommunityUseCase,
) : ViewModel() {
    private val _newCommentStateFlow = MutableStateFlow(CommunityCommentsState())
    val newCommentStateFlow: StateFlow<CommunityCommentsState> = _newCommentStateFlow.asStateFlow()

    private val _commentReplyStateFlow = MutableStateFlow(CommentReplyState())
    val commentReplyStateFlow: StateFlow<CommentReplyState> = _commentReplyStateFlow.asStateFlow()

    private var _allCommentsStateFlow = MutableStateFlow(AllCommentsState())
    val allCommentsStateFlow: StateFlow<AllCommentsState> = _allCommentsStateFlow.asStateFlow()

    private var _allRepliesStateFlow = MutableStateFlow(AllRepliesState())
    val allRepliesStateFlow: StateFlow<AllRepliesState> = _allRepliesStateFlow.asStateFlow()

    private var token = ""
    private var postId = ""

    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _networkStatus.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing.asStateFlow()

    private val dateFormat = SimpleDateFormat("d MMMM, yy", Locale.getDefault())

    init {
        // token may not change during a session (login-logout) - so using init{} else could have used : get()
        token = CommunityUtils.getAuthToken(context)
        postId = CommunityUtils.getOneTimePostId(context)
        observeNetworkConnectivity()
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkObserver.observe().collect { status ->
                if (_networkStatus.value != ConnectivityObserver.Status.Available && status == ConnectivityObserver.Status.Available) {
//                    getAllCommunityPosts()
                }
                _networkStatus.value = status
            }
        }
    }

    fun addNewComment(userComment: String) {
        communityUseCase(token, AddCommentData(userComment, postId)).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _refreshing.update { true }
                    val state = CommunityCommentsState(isLoading = true)
                    _newCommentStateFlow.value = state
                }

                is ResponseResult.Success -> {
                    _refreshing.update { false }
                    val state = CommunityCommentsState(data = result.data)
                    _newCommentStateFlow.value = state
                    if (state.data?.success == "1") {
                        getAllComments()
                    }
                }

                is ResponseResult.Error -> {
                    _refreshing.update { false }
                    val state = CommunityCommentsState(error = result.message ?: UNEXPECTED_ERROR)
                    _newCommentStateFlow.value = state
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllComments() {
        communityUseCase(token, AllCommentsRequestBody(postId = postId)).onEach { response ->
            when (response) {
                is ResponseResult.Loading -> {
                    _refreshing.update { true }
                    _allCommentsStateFlow.update { AllCommentsState(isLoading = true) }
                }

                is ResponseResult.Success -> {
                    _refreshing.update { false }
                    val result = response.data?.allComments?.sortedByDescending {
                        dateFormat.parse(it.commentTime ?: "")
                    }
                    _allCommentsStateFlow.update { it.copy(data = result) }
                }

                is ResponseResult.Error -> {
                    _refreshing.update { false }
                    val error = response.errorBody
                    _allCommentsStateFlow.update { AllCommentsState(error = error) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllReplies(commentId : String) {
        communityUseCase(token, commentId).onEach { response ->
            when (response) {
                is ResponseResult.Loading -> {
                    _refreshing.update { true }
                    _allRepliesStateFlow.update { AllRepliesState(isLoading = true) }
                }

                is ResponseResult.Success -> {
                    _refreshing.update { false }
                    val result = response.data?.data?.map {
                        CommentData(
                            commentId = it?._id,
                            commentContent = it?.commentContent,
                            commentTime = it?.commentTime?.formattedCommentTime(),
                            commentedByUserId = it?.commentedByUserId,
                            commentedByUserName = it?.commentedByUserName,
                            likes = it?.likes?.map {  like ->
                                Like(
                                    likeId = like?._id,
                                    likedByUserId = like?.likedByUserId,
                                    likedByUserName = like?.likedByUserName
                                )
                            },
                            postId = it?.postId,
                            replies = it?.replies
                        )
                    }?.sortedByDescending {
                        dateFormat.parse(it.commentTime ?: "")
                    }
                    _allRepliesStateFlow.update { it.copy(data = AllRepliesData(data = result)) }
                }

                is ResponseResult.Error -> {
                    _refreshing.update { false }
                    val error = response.errorBody
                    _allRepliesStateFlow.update { AllRepliesState(error = error) }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateComment(reply: UpdateCommentData) {
        communityUseCase(token, reply).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _refreshing.update { true }
                    val state = CommentReplyState(isLoading = true)
                    _commentReplyStateFlow.value = state
                }

                is ResponseResult.Success -> {
                    _refreshing.update { false }
                    val state = CommentReplyState(data = result.data)
                    _commentReplyStateFlow.value = state
                }

                is ResponseResult.Error -> {
                    _refreshing.update { false }
                    val state = CommentReplyState(error = result.message ?: UNEXPECTED_ERROR)
                    _commentReplyStateFlow.value = state
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class CommunityCommentsState(
    var isLoading: Boolean? = false,
    var data: AddCommentResponse? = null,
    var error: String? = null,
)

data class AllCommentsState(
    var isLoading: Boolean? = false,
    var data: List<CommentData>? = null,
    var error: String? = null,
)

data class CommentReplyState(
    var isLoading: Boolean? = false,
    var data: UpdatedCommentResponse? = null,
    var error: String? = null,
)

data class AllRepliesState(
    var isLoading: Boolean? = false,
    var data: AllRepliesData? = null,
    var error: String? = null,
)