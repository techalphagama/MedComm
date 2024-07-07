package com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl

import com.alpharays.alaskagemsdk.AlaskaGemSdkConstants.SOMETHING_WENT_WRONG
import com.alpharays.alaskagemsdk.network.ResponseHandler
import com.alpharays.alaskagemsdk.network.ResponseResult
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote.CommunityApiServices
import com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers.toAddNewChatDtoMapper
import com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers.toGetAllChatsDtoMapper
import com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers.toGetInboxListDtoMapper
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.add_new_chat.AddNewChatResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.GetAllChatsResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_inbox_list.GetInboxListResponse
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.MessagesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MessagesRepositoryImpl @Inject constructor(
    private val apiServices: CommunityApiServices,
    private val responseHandler: ResponseHandler,
) : MessagesRepository {
    override suspend fun startNewChat(token: String, receiverId: String): ResponseResult<AddNewChatResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        Response.success(apiServices.startNewChat(token, receiverId).body()?.toAddNewChatDtoMapper())
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllInboxMessagesList(token: String): ResponseResult<GetInboxListResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        Response.success(apiServices.getAllInboxMessagesList(token).body()?.toGetInboxListDtoMapper())
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }

    override suspend fun getAllMessagesList(token: String, chatId: String): ResponseResult<GetAllChatsResponse> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                responseHandler.callAPI {
                    withContext(Dispatchers.IO) {
                        Response.success(apiServices.getAllMessagesList(token, chatId).body()?.toGetAllChatsDtoMapper())
                    }
                }
            } catch (e: Exception) {
                ResponseResult.Error(SOMETHING_WENT_WRONG)
            }
        }
}