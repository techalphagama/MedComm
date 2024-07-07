package com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


data class Spacing(
    val default: Dp = 0.dp,
    /**2.dp**/
    val oneDp : Dp = 1.dp,
    val smallest: Dp = 2.dp,
    val avgExtraSmall: Dp = 3.dp,
    val extraSmall: Dp = 4.dp,
    val lessSmall: Dp = 5.dp,
    val avgLessSmall: Dp = 6.dp,
    val small: Dp = 8.dp,
    val btwAvgSmall: Dp = 9.dp,
    val avgSmall: Dp = 10.dp,
    val medSmall: Dp = 12.dp,
    val medSmallAvg: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val avgMedium: Dp = 20.dp,
    val medLarge: Dp = 24.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp
)


val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
