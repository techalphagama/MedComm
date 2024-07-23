package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.UNEXPECTED_ERROR
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.AllCommunityPostsData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.ReactionType
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.PostReactionResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.reactions.ReactionBody
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.ReactionPainters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    context: Context,
    networkObserver: NetworkConnectivityObserver,
    private val communityUseCase: CommunityUseCase,
) : ViewModel() {
    private val _allCommunityPostsStateFlow = MutableStateFlow(CommunityAllPostsState())
    val allCommunityPostsStateFlow: StateFlow<CommunityAllPostsState> = _allCommunityPostsStateFlow.asStateFlow()

    private val _addNewCommunityPostStateFlow = MutableStateFlow(NewPostResponseState())
    val addNewCommunityPostStateFlow: StateFlow<NewPostResponseState> = _addNewCommunityPostStateFlow.asStateFlow()

    private val _postReactionStateFlow = MutableStateFlow(PostReactionState())
    val postReactionStateFlow: StateFlow<PostReactionState> = _postReactionStateFlow.asStateFlow()

    private var token = ""
    private var canHitApi = true
    private var vmNetworkStatus: ConnectivityObserver.Status? = null
    val appNetworkStatus = networkObserver.observe()

    private val _isRefreshing = MutableStateFlow<Boolean?>(null)
    val isRefreshing = _isRefreshing.asStateFlow()

    val currentNetworkStatus = networkObserver.isNetworkAvailable()

    init {
        // token may not change during a session (login-logout) - so using init{} else could have used : get()
        token = CommunityUtils.getAuthToken(context)
        appNetworkStatus
            .onEach {
                if (it == ConnectivityObserver.Status.Available) {
                    vmNetworkStatus = it
                    if (canHitApi) {
                        getAllCommunityPosts()
                        canHitApi = false
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh () {
        if (vmNetworkStatus == ConnectivityObserver.Status.Available) {
            getAllCommunityPosts()
        }
    }

    private fun getAllCommunityPosts() {
        println("checking_loading_state")
        communityUseCase(token).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    _isRefreshing.update { true }
                    val state = CommunityAllPostsState(isLoading = true)
                    _allCommunityPostsStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    _isRefreshing.update { false }
                    val state = CommunityAllPostsState(isLoading = false, data = result.data)
                    _allCommunityPostsStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    _isRefreshing.update { false }
                    val state = CommunityAllPostsState(isLoading = false, error = result.message ?: UNEXPECTED_ERROR)
                    _allCommunityPostsStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addNewCommunityPost(addNewCommunityPost: AddNewCommunityPost) {
        communityUseCase(token, addNewCommunityPost).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = NewPostResponseState(isLoading = true)
                    _addNewCommunityPostStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    val state = NewPostResponseState(newPostResponse = result.data)
                    _addNewCommunityPostStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    val state = NewPostResponseState(error = result.message ?: UNEXPECTED_ERROR)
                    _addNewCommunityPostStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updatePostReaction(postId: String?, reaction : String) {
        communityUseCase(token, ReactionBody(postId, reaction.lowercase())).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = PostReactionState(isLoading = true)
                    _postReactionStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    val state = PostReactionState(isLoading = false, data = result.data)
                    if(state.data?.success?.toString() == MedCommRouter.SUCCESS_CODE){
                        _allCommunityPostsStateFlow.update {
                            val data = it.data?.allPosts
                            it.copy(
                                data = AllCommunityPostsData(
                                    allPosts = data?.map { post ->
                                        if(post.postId != postId){
                                            post
                                        }
                                        else{
                                            CommunityPost(
                                                comments = post.comments,
                                                postId = post.postId,
                                                myReaction = post.myReaction?.copy(
                                                    reaction =  reaction.lowercase(),
                                                    name = post.myReaction.name,
                                                    reactionId = post.myReaction.reactionId // TODO: reactionId set same - but changes each time reaction changes in server
                                                ),
                                                postContent = post.postContent,
                                                postTitle = post.postTitle,
                                                aboutDoc = post.aboutDoc,
                                                doctorAvatar = post.doctorAvatar,
                                                posterId = post.posterId,
                                                posterName = post.posterName,
                                                reactions = post.reactions?.copy(
                                                    like = if(reaction.lowercase() == ReactionPainters.Like.getLowercaseName()) {
                                                        val currReactions = post.reactions.like?.toMutableList()
                                                        currReactions?.add(ReactionType(
                                                            name = "dummy", // todo : name
                                                            reaction = reaction.lowercase(),
                                                            reactionId = UUID.randomUUID().toString()  // todo : name
                                                        ))
                                                        currReactions
                                                    }
                                                    else {
                                                        post.reactions.like
                                                    },
                                                    love = if(reaction.lowercase() == ReactionPainters.Love.getLowercaseName()) {
                                                        val currReactions = post.reactions.love?.toMutableList()
                                                        currReactions?.add(ReactionType(
                                                            name = "dummy", // todo : name
                                                            reaction = reaction.lowercase(),
                                                            reactionId = UUID.randomUUID().toString()  // todo : name
                                                        ))
                                                        currReactions
                                                    }
                                                    else {
                                                        post.reactions.love
                                                    },
                                                    celebrate = if(reaction.lowercase() == ReactionPainters.Celebrate.getLowercaseName()) {
                                                        val currReactions = post.reactions.celebrate?.toMutableList()
                                                        currReactions?.add(ReactionType(
                                                            name = "dummy", // todo : name
                                                            reaction = reaction.lowercase(),
                                                            reactionId = UUID.randomUUID().toString()  // todo : name
                                                        ))
                                                        currReactions
                                                    }
                                                    else {
                                                        post.reactions.celebrate
                                                    },
                                                    insightful = if(reaction.lowercase() == ReactionPainters.Insightful.getLowercaseName()) {
                                                        val currReactions = post.reactions.insightful?.toMutableList()
                                                        currReactions?.add(ReactionType(
                                                            name = "dummy", // todo : name
                                                            reaction = reaction.lowercase(),
                                                            reactionId = UUID.randomUUID().toString()  // todo : name
                                                        ))
                                                        currReactions
                                                    }
                                                    else {
                                                        post.reactions.insightful
                                                    },
                                                    funny = if(reaction.lowercase() == ReactionPainters.Funny.getLowercaseName()) {
                                                        val currReactions = post.reactions.funny?.toMutableList()
                                                        currReactions?.add(ReactionType(
                                                            name = "dummy", // todo : name
                                                            reaction = reaction.lowercase(),
                                                            reactionId = UUID.randomUUID().toString()  // todo : name
                                                        ))
                                                        currReactions
                                                    }
                                                    else {
                                                        post.reactions.funny
                                                    },
                                                    support = if(reaction.lowercase() == ReactionPainters.Support.getLowercaseName()) {
                                                        val currReactions = post.reactions.support?.toMutableList()
                                                        currReactions?.add(ReactionType(
                                                            name = "dummy", // todo : name
                                                            reaction = reaction.lowercase(),
                                                            reactionId = UUID.randomUUID().toString()  // todo : name
                                                        ))
                                                        currReactions
                                                    }
                                                    else {
                                                        post.reactions.support
                                                    }
                                                )
                                            )
                                        }
                                    }
                                )
                            )
                        }
                    }
                    _postReactionStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    val state = PostReactionState(isLoading = false, error = result.message ?: UNEXPECTED_ERROR)
                    _postReactionStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class CommunityAllPostsState(
    var isLoading: Boolean? = null,
    var data: AllCommunityPostsData? = null,
    var error: String? = null,
)

data class NewPostResponseState(
    var isLoading: Boolean? = false,
    var newPostResponse: NewPostResponse? = null,
    var error: String? = null,
)

data class PostReactionState(
    var isLoading: Boolean? = false,
    var data: PostReactionResponse? = null,
    var error: String? = null,
)