package com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers

import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_all_chats_dto.GetAllChatsResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.ChatsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.GetAllChatsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.MessageResponse

fun GetAllChatsResponseDto.toGetAllChatsDtoMapper(): GetAllChatsResponse? {
    val response = this.data
    return try {
        GetAllChatsResponse(
            chatsData = response?.chatsdata?.map {
                ChatsResponse(
                    createdAt = it?.createdAt,
                    messages = it?.messages?.map { message ->
                        MessageResponse(
                            messageId = message?._id,
                            messageContent = message?.messageContent,
                            senderId = message?.senderId,
                            sentTime = message?.sentTime
                        )
                    },
                    room = it?.room
                )
            },
            senderCheckId = response?.id
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}