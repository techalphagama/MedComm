package com.alpharays.mymedicommfma.communityv2.community_app.presentation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.AppNavGraph
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.CommunityTheme

@Composable
fun AppContent() {
    CommunityTheme {
        val navController = rememberNavController()
        Scaffold(modifier = Modifier.navigationBarsPadding()) { innerPaddingModifier ->
            AppNavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPaddingModifier)
            )
        }
    }
}