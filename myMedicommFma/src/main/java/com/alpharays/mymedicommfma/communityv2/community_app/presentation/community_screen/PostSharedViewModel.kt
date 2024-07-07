package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.ReactionType
import com.alpharays.mymedicommfma.communityv2.community_app.domain.usecase.DatastoreUseCase
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.ReactionPainters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostSharedViewModel @Inject constructor(
    private val useCase: DatastoreUseCase,
) : ViewModel() {
    private val _postContentState = MutableStateFlow<CommunityPost?>(CommunityPost())
    val postContentState: StateFlow<CommunityPost?> = _postContentState

    private val savedReactions = hashMapOf<String, List<ReactionType?>?>()

    init {
        getCurrentPostState()
    }

    fun setCurrentPostState(post: CommunityPost) {
        savedReactions.clear()
        viewModelScope.launch {
            useCase.setCommunityPost(post)
        }
    }

    fun getTopTwoValidReactions(): List<ReactionType> {
        return savedReactions.values
            .flatMap { it ?: listOf() } // Flatten and handle null lists
            .filterNotNull() // Remove null reactions
            .distinct() // Remove duplicates
            .take(2)
    }

    private fun getCurrentPostState() {
        useCase.getCommunityPost()
            .onEach { result ->
                result?.reactions?.let { reactions ->
                    savedReactions[ReactionPainters.Like.getLowercaseName()] = reactions.like
                    savedReactions[ReactionPainters.Funny.getLowercaseName()] = reactions.funny
                    savedReactions[ReactionPainters.Celebrate.getLowercaseName()] = reactions.celebrate
                    savedReactions[ReactionPainters.Insightful.getLowercaseName()] = reactions.insightful
                    savedReactions[ReactionPainters.Love.getLowercaseName()] = reactions.love
                    savedReactions[ReactionPainters.Support.getLowercaseName()] = reactions.support
                }
                _postContentState.value = result
            }
            .launchIn(viewModelScope)
    }

    fun removeAllPosts() {
        viewModelScope.launch {
            useCase.removeAllPosts()
        }
    }
}