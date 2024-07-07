package com.alpharays.mymedicommfma.communityv2.community_app.domain.repository

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.MessageResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.socketio.MessagesSocketState
import kotlinx.coroutines.flow.StateFlow

interface SocketIO{
    val messagesSocketState: StateFlow<MessagesSocketState>
    val inboxSocketState: StateFlow<MessagesSocketState>
    val joinRoomSocketState: StateFlow<MessagesSocketState>
    val messageRoomSocketState: StateFlow<MessagesSocketState>

    // inbox screen socket handler
    fun connectInboxSocket()
    fun disconnectInboxSocket()


    // direct message screen socket handler
    fun connectJoinRoomSocket()
    fun disconnectJoinRoomSocket()

    // message room socket handler
    fun connectMessagesRoomSocket()
    fun disconnectMessagesRoomSocket()


    // reply event socket handler
    fun connectMessagesSocket()
    fun disconnectMessagesSocket()

    fun emitDirectMessage(newMessageMap: Map<String, MessageResponse>)

}