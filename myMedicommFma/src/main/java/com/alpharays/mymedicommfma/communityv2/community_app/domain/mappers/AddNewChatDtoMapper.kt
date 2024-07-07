package com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers

import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.add_new_chat_dto.AddNewChatResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.add_new_chat.AddNewChatResponse

fun AddNewChatResponseDto.toAddNewChatDtoMapper(): AddNewChatResponse? {
    val response = this.data
    return try {
        AddNewChatResponse(
            chatId = response?._id,
            createdAt = response?.createdAt,
            messages = response?.messages,
            participants = response?.participants,
            room = response?.room
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}