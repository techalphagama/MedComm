package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.message_inbox

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list.GetInboxListResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.MessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    context: Context,
    private val messagesUseCase: MessagesUseCase,
) : ViewModel() {
    private val _getInboxListStateFlow = MutableStateFlow(InboxMessageListState())
    val getInboxListStateFlow: StateFlow<InboxMessageListState> = _getInboxListStateFlow.asStateFlow()
    var token : String

    init {
        token = CommunityUtils.getAuthToken(context)
        getAllInboxMessages()
    }

    private fun getAllInboxMessages() {
        messagesUseCase(token).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = InboxMessageListState(isLoading = true)
                    _getInboxListStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    val state = InboxMessageListState(data = result.data)
                    _getInboxListStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    val state = InboxMessageListState(error = result.message ?: MedCommRouter.UNEXPECTED_ERROR)
                    _getInboxListStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class InboxMessageListState(
    val isLoading: Boolean? = null,
    val data: GetInboxListResponse? = null,
    val error: String? = null,
)