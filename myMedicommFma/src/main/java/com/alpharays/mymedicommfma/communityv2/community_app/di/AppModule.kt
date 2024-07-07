package com.alpharays.mymedicommfma.communityv2.community_app.di

import android.content.Context
import android.content.SharedPreferences
import com.alpharays.alaskagemsdk.network.ResponseHandler
import com.alpharays.mymedicommfma.common.connectivity.NetworkConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.API_SAFE_KEY
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.API_SAFE_KEY_VALUE
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.BASE_URL
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.SHARED_PREFERENCE_NAME
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.remote.CommunityApiServices
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.CommunityApiImpl
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.DatastoreRepositoryImpl
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.MessagesRepositoryImpl
import com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl.SocketIOImpl
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.CommunityApi
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.DatastoreRepository
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.MessagesRepository
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.SocketIO
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHttpClient(): CommunityApiServices {
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

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().setPrettyPrinting().create()
                )
            )
            .build()
            .create(CommunityApiServices::class.java)
    }

    @Provides
    @Singleton
    fun getResponseHandler(): ResponseHandler = ResponseHandler()

    @Provides
    @Singleton
    fun provideCommunityRepository(
        apiServices: CommunityApiServices,
        responseHandler: ResponseHandler,
    ): CommunityApi {
        return CommunityApiImpl(apiServices, responseHandler)
    }

    @Provides
    @Singleton
    fun provideMessagesRepository(
        apiServices: CommunityApiServices,
        responseHandler: ResponseHandler,
    ): MessagesRepository {
        return MessagesRepositoryImpl(apiServices, responseHandler)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    @Singleton
    fun provideNetworkConnectivityObserver(@ApplicationContext context: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(context)
    }

    @Singleton
    @Provides
    fun provideDatastore(@ApplicationContext context: Context): DatastoreRepository {
        return DatastoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideSocketIo(): SocketIO {
        return SocketIOImpl()
    }
}