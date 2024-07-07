package com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl

import android.util.Log
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.APP_TAG
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.APP_TAG_ERROR
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.SOCKET_BASE_URL
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.JOIN_ROOM
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.MESSAGES_ROOM
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.MESSAGE_REPLY_EVENT
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SEND_MESSAGE
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.MessageResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.socketio.MessagesSocketState
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.socketio.SocketMessages
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.SocketIO
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SocketIOImpl : SocketIO {
    private var inboxSocket: Socket?
    private var joinRoomSocket: Socket?
    private var messageRoomSocket: Socket?
    private var messagesSocket: Socket?

    init {
        inboxSocket = createSocket()
        joinRoomSocket = createSocket()
        messageRoomSocket = createSocket()
        messagesSocket = createSocket()
    }

    private val _inboxSocketState = MutableStateFlow(MessagesSocketState())
    override val inboxSocketState: StateFlow<MessagesSocketState> = _inboxSocketState.asStateFlow()

    private val _joinRoomSocketState = MutableStateFlow(MessagesSocketState())
    override val joinRoomSocketState: StateFlow<MessagesSocketState> = _joinRoomSocketState.asStateFlow()

    private val _messageRoomSocketState = MutableStateFlow(MessagesSocketState())
    override val messageRoomSocketState: StateFlow<MessagesSocketState> = _messageRoomSocketState.asStateFlow()

    private val _messagesSocketState = MutableStateFlow(MessagesSocketState())
    override val messagesSocketState: StateFlow<MessagesSocketState> = _messagesSocketState.asStateFlow()

    private fun createSocket(): Socket? {
        val url = SOCKET_BASE_URL
        return try {
            val options = IO.Options()
            options.transports = arrayOf(WebSocket.NAME)
            options.reconnection = true
            IO.socket(url, options).apply {
                on(Socket.EVENT_CONNECT) { Log.d(APP_TAG, "Connected to $url") }
                on(Socket.EVENT_DISCONNECT) { Log.d(APP_TAG, "Disconnected from $url") }
                on(Socket.EVENT_CONNECT_ERROR) { Log.d(APP_TAG,"Connection error to $url: ${it.contentToString()}")  }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(APP_TAG_ERROR, e.printStackTrace().toString())
            null
        }
    }

    override fun connectInboxSocket() {
        val scope = CoroutineScope(Dispatchers.IO)
        inboxSocket?.connect()
        inboxSocket?.on(MESSAGE_REPLY_EVENT) { args ->
            if (!args.isNullOrEmpty()) {
                val dataString = args.first().toString()
                scope.launch {
                    try {
                        val messagesData = Gson().fromJson(dataString, SocketMessages::class.java)
                        val currentState = _messagesSocketState.value.data
                        val updatedData = SocketMessages(
                            senderId = messagesData?.senderId ?: currentState?.senderId,
                            message = messagesData?.message ?: currentState?.message,
                            receiverId = messagesData?.receiverId ?: currentState?.receiverId
                        )
                        val state = _inboxSocketState.value.copy(data = updatedData)
                        _inboxSocketState.emit(state)
                    } catch (e: Exception) {
                        val state = _inboxSocketState.value.copy(data = null)
                        _inboxSocketState.emit(state)
                    }
                }
            }
        }
        inboxSocket?.on(Socket.EVENT_CONNECT) { Log.d(APP_TAG, "EVENT_CONNECT: messages") }
        inboxSocket?.on(Socket.EVENT_DISCONNECT) { Log.d(APP_TAG, "EVENT_DISCONNECT: messages") }
        inboxSocket?.on(Socket.EVENT_CONNECT_ERROR) { Log.d(APP_TAG,"EVENT_ERROR messages : ${it.contentToString()}") }
    }

    override fun disconnectInboxSocket() {
        inboxSocket?.off(MESSAGE_REPLY_EVENT)
        inboxSocket?.disconnect()
    }

    override fun connectJoinRoomSocket() {
        val scope = CoroutineScope(Dispatchers.IO)
        joinRoomSocket?.connect()
        joinRoomSocket?.on(JOIN_ROOM) { args ->
            if (!args.isNullOrEmpty()) {
                val dataString = args.first().toString()
                scope.launch {
                    try {
                        val messagesData = Gson().fromJson(dataString, SocketMessages::class.java)
                        val currentState = _joinRoomSocketState.value.data
                        val updatedData = SocketMessages(
                            senderId = messagesData?.senderId ?: currentState?.senderId,
                            message = messagesData?.message ?: currentState?.message,
                            receiverId = messagesData?.receiverId ?: currentState?.receiverId
                        )
                        val state = _joinRoomSocketState.value.copy(data = updatedData)
                        _joinRoomSocketState.emit(state)
                    } catch (e: Exception) {
                        val state = _joinRoomSocketState.value.copy(data = null)
                        _joinRoomSocketState.emit(state)
                    }
                }
            }
        }
        joinRoomSocket?.on(Socket.EVENT_CONNECT) { Log.d(APP_TAG, "EVENT_CONNECT: messages") }
        joinRoomSocket?.on(Socket.EVENT_DISCONNECT) { Log.d(APP_TAG, "EVENT_DISCONNECT: messages") }
        joinRoomSocket?.on(Socket.EVENT_CONNECT_ERROR) { Log.d(APP_TAG,"EVENT_ERROR messages : ${it.contentToString()}") }
    }

    override fun disconnectJoinRoomSocket() {
        joinRoomSocket?.off(JOIN_ROOM)
        joinRoomSocket?.disconnect()
    }

    override fun connectMessagesRoomSocket() {
        val scope = CoroutineScope(Dispatchers.IO)
        messageRoomSocket?.connect()
        messageRoomSocket?.on(MESSAGES_ROOM) { args ->
            if (!args.isNullOrEmpty()) {
                val dataString = args.first().toString()
                scope.launch {
                    try {
                        val messagesData = Gson().fromJson(dataString, SocketMessages::class.java)
                        val currentState = _messageRoomSocketState.value.data
                        val updatedData = SocketMessages(
                            senderId = messagesData?.senderId ?: currentState?.senderId,
                            message = messagesData?.message ?: currentState?.message,
                            receiverId = messagesData?.receiverId ?: currentState?.receiverId
                        )
                        val state = _messageRoomSocketState.value.copy(data = updatedData)
                        _messageRoomSocketState.emit(state)
                    } catch (e: Exception) {
                        val state = _messageRoomSocketState.value.copy(data = null)
                        _messageRoomSocketState.emit(state)
                    }
                }
            }
        }
        messageRoomSocket?.on(Socket.EVENT_CONNECT) { Log.d(APP_TAG, "EVENT_CONNECT: messages") }
        messageRoomSocket?.on(Socket.EVENT_DISCONNECT) { Log.d(APP_TAG, "EVENT_DISCONNECT: messages") }
        messageRoomSocket?.on(Socket.EVENT_CONNECT_ERROR) { Log.d(APP_TAG,"EVENT_ERROR messages : ${it.contentToString()}") }
    }

    override fun disconnectMessagesRoomSocket() {
        messageRoomSocket?.off(MESSAGES_ROOM)
        messageRoomSocket?.disconnect()
    }

    override fun connectMessagesSocket() {
        val scope = CoroutineScope(Dispatchers.IO)
        messagesSocket?.connect()
        messagesSocket?.on(MESSAGE_REPLY_EVENT) { args ->
            if (!args.isNullOrEmpty()) {
                val dataString = args.first().toString()
                scope.launch {
                    try {
                        val messagesData = Gson().fromJson(dataString, SocketMessages::class.java)
                        val currentState = _messagesSocketState.value.data
                        val updatedData = SocketMessages(
                            senderId = messagesData?.senderId ?: currentState?.senderId,
                            message = messagesData?.message ?: currentState?.message,
                            receiverId = messagesData?.receiverId ?: currentState?.receiverId
                        )
                        val state = _messagesSocketState.value.copy(data = updatedData)
                        _messagesSocketState.emit(state)
                    } catch (e: Exception) {
                        val state = _messagesSocketState.value.copy(data = null)
                        _messagesSocketState.emit(state)
                    }
                }
            }
        }
        messagesSocket?.on(Socket.EVENT_CONNECT) { Log.d(APP_TAG, "EVENT_CONNECT: messages") }
        messagesSocket?.on(Socket.EVENT_DISCONNECT) { Log.d(APP_TAG, "EVENT_DISCONNECT: messages") }
        messagesSocket?.on(Socket.EVENT_CONNECT_ERROR) { Log.d(APP_TAG,"EVENT_ERROR messages : ${it.contentToString()}")  }
    }

    override fun disconnectMessagesSocket() {
        messagesSocket?.off(MESSAGE_REPLY_EVENT)
        messagesSocket?.disconnect()
    }

    override fun emitDirectMessage(newMessageMap: Map<String, MessageResponse>) {
        messagesSocket?.emit(SEND_MESSAGE, newMessageMap)
    }

}