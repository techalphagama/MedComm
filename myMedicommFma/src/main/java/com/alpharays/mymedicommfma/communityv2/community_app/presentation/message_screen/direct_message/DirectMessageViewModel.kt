package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.add_new_chat.AddNewChatResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.GetAllChatsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.SocketIO
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
class DirectMessageViewModel @Inject constructor(
    context: Context,
    private val socketIO: SocketIO,
    private val messagesUseCase: MessagesUseCase,
) : ViewModel() {
    private val _getNewChatStateFlow = MutableStateFlow(AddNewChatState())
    val getNewChatStateFlow: StateFlow<AddNewChatState> = _getNewChatStateFlow.asStateFlow()

    private val _getAllChatsStateFlow = MutableStateFlow(DirectMessageState())
    val getAllChatsStateFlow: StateFlow<DirectMessageState> = _getAllChatsStateFlow.asStateFlow()

    var token = ""

    init {
//        connectToJoinRoomSocket()
//        connectToMessagesSocket()
        token = CommunityUtils.getAuthToken(context)
    }

    fun startNewChat(receiverId : String) {
        messagesUseCase.addNewChat(token, receiverId).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = AddNewChatState(isLoading = true)
                    _getNewChatStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    val state = AddNewChatState(data = result.data)
                    state.data?.chatId?.let { chatId ->
                        getAllMessages(chatId)
                    }
                    _getNewChatStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    val state = AddNewChatState(error = result.message ?: MedCommRouter.UNEXPECTED_ERROR)
                    _getNewChatStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllMessages(chatId : String) {
        messagesUseCase(token, chatId).onEach { result ->
            when (result) {
                is ResponseResult.Loading -> {
                    val state = DirectMessageState(isLoading = true)
                    _getAllChatsStateFlow.update { state }
                }

                is ResponseResult.Success -> {
                    val state = DirectMessageState(data = result.data)
                    _getAllChatsStateFlow.update { state }
                }

                is ResponseResult.Error -> {
                    val state = DirectMessageState(error = result.message ?: MedCommRouter.UNEXPECTED_ERROR)
                    _getAllChatsStateFlow.update { state }
                }
            }
        }.launchIn(viewModelScope)
    }

//    fun sendMessage(messageData: SnapshotStateMap<String, DirectMessage>) {
//        socketIO.emitDirectMessage(messageData.toMap())
//    }

    private fun connectToMessagesSocket() {
        socketIO.connectMessagesSocket()
    }

    private fun disConnectMessagesSocket() {
        socketIO.disconnectMessagesSocket()
    }

    private fun connectToJoinRoomSocket() {
        socketIO.connectJoinRoomSocket()
    }

    private fun disConnectJoinRoomSocket() {
        socketIO.disconnectJoinRoomSocket()
    }

    override fun onCleared() {
        super.onCleared()
        disConnectMessagesSocket()
    }

}

data class AddNewChatState(
    val isLoading: Boolean? = null,
    val data: AddNewChatResponse? = null,
    val error: String? = null,
)

data class DirectMessageState(
    val isLoading: Boolean? = null,
    val data: GetAllChatsResponse? = null,
    val error: String? = null,
)
