package com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Sizes(
    val default: Dp = 0.dp,
    val smallest: Dp = 2.dp,
    val moreExtraSmall: Dp = 3.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val smallMed: Dp = 7.dp,
    val avgSmall: Dp = 10.dp,
    val medSmall: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val avgMedium : Dp = 18.dp,
    val medLarge: Dp = 24.dp,
    /**20.dp**/
    val smallestIconSize: Dp = 16.dp,
    val smallIconSize: Dp = 20.dp,
    val extraSmallIconSize: Dp = 18.dp,
    val defaultIconSize: Dp = 24.dp,
    val defaultSmallIconSize: Dp = 22.dp,
    val mediumIconSize: Dp = 28.dp,
    val mediumIconSize2: Dp = 30.dp,
    val largeIconSize: Dp = 32.dp,
    val large: Dp = 32.dp,
    val large2: Dp = 40.dp,
    val large3: Dp = 48.dp,
    val extraLarge: Dp = 64.dp,
    val largeImage: Dp = 80.dp,
    val largestImage: Dp = 100.dp,
    val doubleExtraLarge: Dp = 100.dp,
    val lightDivider: Dp = 0.5.dp,
    val borderStrokeWidth: Dp = 1.dp,
    val large1: Dp = 36.dp
)


data class FontSizes(
    val default: TextUnit = 14.sp,
    val smallest: TextUnit = 8.sp,
    val extraSmall: TextUnit = 9.sp,
    val small: TextUnit = 10.sp,
    val medSmall: TextUnit = 12.sp,
    val medSmall2: TextUnit = 14.sp,
    val medium: TextUnit = 16.sp,
    val medLarge: TextUnit = 18.sp,
    val large: TextUnit = 20.sp,
    val minLetterSpacing: TextUnit = 0.6.sp
)

val LocalSize = compositionLocalOf { Sizes() }

val MaterialTheme.size: Sizes
    @Composable
    @ReadOnlyComposable
    get() = LocalSize.current


val LocalFontSize = compositionLocalOf { FontSizes() }


val MaterialTheme.fontSize: FontSizes
    @Composable
    @ReadOnlyComposable
    get() = LocalFontSize.current
