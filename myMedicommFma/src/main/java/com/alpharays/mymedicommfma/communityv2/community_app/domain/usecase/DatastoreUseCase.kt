package com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase

import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatastoreUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository,
) {
    suspend fun setCommunityPost(post: CommunityPost) {
        datastoreRepository.setCommunityPost(post)
    }

    fun getCommunityPost(): Flow<CommunityPost?> {
        return datastoreRepository.getCommunityPost()
    }

    suspend fun removeAllPosts() {
        datastoreRepository.removeAllAlerts()
    }
}