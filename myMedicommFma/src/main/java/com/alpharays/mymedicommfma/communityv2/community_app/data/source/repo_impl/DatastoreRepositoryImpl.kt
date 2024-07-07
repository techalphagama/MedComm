package com.alpharays.mymedicommfma.communityv2.community_app.data.source.repo_impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityPostDeserializer
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.DatastoreRepository
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("PREFERENCES_DATA_STORE_FILE_NAME")

class DatastoreRepositoryImpl @Inject constructor(
    private val context: Context,
) : DatastoreRepository {
    private val communityPostKey = stringPreferencesKey("community_post_datastore_key")
    private val gson = GsonBuilder()
        .registerTypeAdapter(CommunityPost::class.java, CommunityPostDeserializer())
        .create()

    override val getCurrentPostStateFlow: MutableStateFlow<CommunityPost?>
        get() = MutableStateFlow(null)

    override suspend fun setCommunityPost(post: CommunityPost) {
        val json = gson.toJson(post)
        context.datastore.edit { preferences ->
            preferences.clear()
            getCurrentPostStateFlow.value = post
            preferences[communityPostKey] = json
        }
    }

    override suspend fun removeCommunityPost(id: String) {
        try {
            context.datastore.edit {
                it.clear()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getCommunityPost(): Flow<CommunityPost?> = context.datastore.data
        .map { preferences ->
            val json = preferences[communityPostKey]
            val result = if (json != null) {
                val type = object : TypeToken<CommunityPost>() {}.type
                gson.fromJson<CommunityPost>(json, type)
            } else {
                null
            }
            getCurrentPostStateFlow.value = result
            result
        }

    override suspend fun removeAllAlerts() {
        try {
            context.datastore.edit {
                it.clear()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}