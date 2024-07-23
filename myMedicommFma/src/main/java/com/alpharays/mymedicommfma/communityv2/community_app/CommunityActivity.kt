package com.alpharays.mymedicommfma.communityv2.community_app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.alpharays.mymedicommfma.communityv2.community_app.di.DependencyProvider
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.AppContent
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.CommunityTheme
import com.alpharays.mymedicommfma.communityv2.navigation.CommunityFeatureImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DependencyProvider.provideImpl(communityFeatureApi = CommunityFeatureImpl(this))
        setContent {
            CommunityTheme {
                WindowCompat.setDecorFitsSystemWindows(window, true)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppContent()
                }
            }
        }
    }
}
