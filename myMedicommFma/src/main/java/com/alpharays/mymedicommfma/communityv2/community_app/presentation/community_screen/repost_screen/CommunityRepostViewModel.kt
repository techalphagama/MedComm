package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.repost_screen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.UNEXPECTED_ERROR
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.NewPostResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityRepostViewModel @Inject constructor(
    context: Context,
    private val networkObserver: NetworkConnectivityObserver,
    private val communityUseCase: CommunityUseCase,
) : ViewModel() {
    private val _addNewCommunityPostStateFlow = MutableStateFlow(RepostResponseState())
    val addNewCommunityPostStateFlow: StateFlow<RepostResponseState> = _addNewCommunityPostStateFlow.asStateFlow()

    private var token = ""

    private val _networkStatus = MutableStateFlow(ConnectivityObserver.Status.Unavailable)
    val networkStatus: StateFlow<ConnectivityObserver.Status> = _networkStatus.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing.asStateFlow()

    init {
        // token may not change during a session (login-logout) - so using init{} else could have used : get()
        token = CommunityUtils.getAuthToken(context)
        observeNetworkConnectivity()
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkObserver.observe().collect { status ->
                _networkStatus.update { status }
            }
        }
    }

    fun addNewCommunityPost(addNewCommunityPost: AddNewCommunityPost) {
        communityUseCase(token, addNewCommunityPost).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = RepostResponseState(isLoading = true)
                    _addNewCommunityPostStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    val state = RepostResponseState(response = result.data)
                    _addNewCommunityPostStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    val state = RepostResponseState(error = result.message ?: UNEXPECTED_ERROR)
                    _addNewCommunityPostStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class RepostResponseState(
    var isLoading: Boolean? = false,
    var response: NewPostResponse? = null,
    var error: String? = null,
)