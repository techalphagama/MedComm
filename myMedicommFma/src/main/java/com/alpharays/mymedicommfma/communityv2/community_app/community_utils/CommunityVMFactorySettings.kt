package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified T : ViewModel> getCommunityViewModel(useCase: Any): T {
    return viewModel(
//        factory = CommunityAppViewModelFactory(useCase)
    )
}

@Composable
inline fun <reified T : ViewModel> getCommunitySSHViewModel(useCase: Any): T {
    val owner = LocalSavedStateRegistryOwner.current
    return viewModel(
//        factory = SavedStateHandleViewModelFactory(useCase, owner)
    )
}

//@Suppress("UNCHECKED_CAST")
//class CommunityAppViewModelFactory<T>(private val useCase: T) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return when {
//            modelClass.isAssignableFrom(CommunityViewModel::class.java) -> {
//                CommunityViewModel(useCase as CommunityUseCase) as T
//            }
//
//            modelClass.isAssignableFrom(CurrPostDetailViewModel::class.java) -> {
//                CurrPostDetailViewModel(useCase as CommunityUseCase) as T
//            }
//
//            modelClass.isAssignableFrom(MessagesViewModel::class.java) -> {
//                MessagesViewModel(useCase as MessagesUseCase) as T
//            }
//
//            else -> {
//                Log.e("SOME_VIEW_MODEL", "NOT DEFINED")
//                exitProcess(1)
//            }
//        }
//    }
//}

//@Suppress("UNCHECKED_CAST")
//class SavedStateHandleViewModelFactory(
//    private val useCase: Any,
//    owner: SavedStateRegistryOwner,
//    defaultArgs: Bundle? = null,
//) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
//    override fun <T : ViewModel> create(
//        key: String,
//        modelClass: Class<T>,
//        handle: SavedStateHandle,
//    ): T {
//        return when {
//            // depends on need
//            modelClass.isAssignableFrom(CurrPostDetailViewModel::class.java) -> {
//                CurrPostDetailViewModel(useCase as CommunityUseCase) as T
//            }
//
//            else -> {
//                Log.e("SOME_VIEW_MODEL", "NOT DEFINED")
//                exitProcess(1)
//            }
//        }
//    }
//}
