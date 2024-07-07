package com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers

import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.messages_dto.get_inbox_list_dto.GetInboxListResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list.GetInboxListResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list.InboxResponse

fun GetInboxListResponseDto.toGetInboxListDtoMapper(): GetInboxListResponse? {
    val response = this.data
    return try {
        GetInboxListResponse(
            allMessages = response?.map {
                InboxResponse(
                    chatId = it?.chatId,
                    userImage = it?.chatImage,
                    userName = it?.chatName,
                    createdAt = it?.createdAt,
                    room = it?.room,
                    lastMessage = it?.lastMessage,
                    lastMsgTimeStamp = it?.lastMsgTimeStamp?.formattedCommentTime()
                )
            }
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}