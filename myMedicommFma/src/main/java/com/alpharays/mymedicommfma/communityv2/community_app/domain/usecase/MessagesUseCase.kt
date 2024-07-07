package com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase

import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.add_new_chat.AddNewChatResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.GetAllChatsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list.GetInboxListResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessagesUseCase @Inject constructor(
    private val messagesRepository: MessagesRepository
) {
    fun addNewChat(token: String, receiverId: String): Flow<ResponseResult<AddNewChatResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val startNewChatResponse = messagesRepository.startNewChat(token, receiverId)
            emit(ResponseResult.Success(startNewChatResponse.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }

    operator fun invoke(token: String, chatId: String): Flow<ResponseResult<GetAllChatsResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val myCurrMessages = messagesRepository.getAllMessagesList(token, chatId)
            emit(ResponseResult.Success(myCurrMessages.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }

    operator fun invoke(token: String): Flow<ResponseResult<GetInboxListResponse?>> = flow {
        emit(ResponseResult.Loading())
        try {
            val myInboxMessages = messagesRepository.getAllInboxMessagesList(token)
            emit(ResponseResult.Success(myInboxMessages.data))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseResult.Error(SOMETHING_WENT_WRONG))
        }
    }
}