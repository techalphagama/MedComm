package com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alpharays.alaskagemsdk.core.register
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.alpharays.mymedicommfma.communityv2.community_app.di.DependencyProvider


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = DependencyProvider.communityFeature().communityRoute
    ) {
        register(
            DependencyProvider.communityFeature(),
            navController = navController,
            modifier = modifier
        )
    }

}

