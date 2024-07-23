package com.alpharays.mymedicommfma.communityv2.community_app.data.di

import android.content.Context
import com.alpharays.alaskagemsdk.network.ResponseHandler
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.API_SAFE_KEY
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.API_SAFE_KEY_VALUE
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.BASE_URL
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote.CommunityApiServices
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.CommunityApiImpl
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.SocketIOImpl
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.SocketIO
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.CommunityUseCase
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.MessagesRepositoryImpl
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.MessagesUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MedicoCommunityInjector {

    /**
     * Base url & api services declaration
     */
    private val baseUrl = BASE_URL
  //  private lateinit var apiServices: ApiServices
    private lateinit var communityApiServices: CommunityApiServices
    private lateinit var context: Context


    /**
     * api services initialization with OkHttpClient and retrofit
     */
    @Synchronized
    fun getCommunityApiServices(): CommunityApiServices {
        if (!::communityApiServices.isInitialized) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // Create an OkHttpClient with an interceptor to add the token header
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val newRequest = originalRequest.newBuilder()
                        .header(
                            API_SAFE_KEY,
                            API_SAFE_KEY_VALUE
                        )
                        .build()
                    chain.proceed(newRequest)
                }
                .build()

            communityApiServices = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(
                    GsonConverterFactory.create(getGson())
                )
                .build()
                .create(CommunityApiServices::class.java)
        }
        return communityApiServices
    }


    /**
     * Custom GSon initialization with leniency
     */
    private fun getGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }


    /**
     * Response Handler initialization
     */
    private lateinit var responseHandler: ResponseHandler
    private fun getResponseHandler(): ResponseHandler {
        if (!::responseHandler.isInitialized) {
            responseHandler = ResponseHandler()
        }
        return ResponseHandler()
    }


    /**
     * All posts in community and its useCase and repository implementation
     */
    private lateinit var communityUseCase: CommunityUseCase
    fun getCommunityUseCase(): CommunityUseCase {
        if (!::communityUseCase.isInitialized) {
            val apiServices = getCommunityApiServices()
            val responseHandler = getResponseHandler()
            val impl = CommunityApiImpl(apiServices, responseHandler)
            communityUseCase = CommunityUseCase(impl)
        }
        return communityUseCase
    }


    /**
     * All messages and its useCase and RepositoryImpl
     */
    private lateinit var messagesUseCase: MessagesUseCase
    fun getMessagesUseCase(): MessagesUseCase {
        if (!::messagesUseCase.isInitialized) {
            val apiServices = getCommunityApiServices()
            val responseHandler = getResponseHandler()
            val impl = MessagesRepositoryImpl(apiServices, responseHandler)
            messagesUseCase = MessagesUseCase(impl)
        }
        return messagesUseCase
    }


    /**
     * socket and all its event-listeners-emitters
     */
    private lateinit var socketIOImpl: SocketIO
    fun getSocketIO(): SocketIO {
        if (!::socketIOImpl.isInitialized) {
            socketIOImpl = SocketIOImpl()
        }
        return socketIOImpl
    }

}