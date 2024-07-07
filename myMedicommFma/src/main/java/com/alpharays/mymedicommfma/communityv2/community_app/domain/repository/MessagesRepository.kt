package com.alpharays.mymedicommfma.communityv2.community_app.domain.repository

import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.add_new_chat.AddNewChatResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.GetAllChatsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list.GetInboxListResponse

interface MessagesRepository {
    suspend fun startNewChat(token: String, receiverId: String): ResponseResult<AddNewChatResponse>
    suspend fun getAllInboxMessagesList(token: String): ResponseResult<GetInboxListResponse>
    suspend fun getAllMessagesList(token: String, chatId: String): ResponseResult<GetAllChatsResponse>
}