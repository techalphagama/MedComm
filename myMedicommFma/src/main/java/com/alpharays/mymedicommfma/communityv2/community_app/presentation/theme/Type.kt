package com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.alpharays.mymedicommfma.R

val lobsterFontFamily = FontFamily(Font(R.font.lobster_regular, weight = FontWeight.Normal))

val bebasNeueFontFamily = FontFamily(Font(R.font.bebas_neue_regular, weight = FontWeight.Normal))

val manRopeFontFamily = FontFamily(
    Font(R.font.manrope_extra_light, weight = FontWeight.ExtraLight),
    Font(R.font.manrope_light, weight = FontWeight.Light),
    Font(R.font.manrope_regular, weight = FontWeight.Normal),
    Font(R.font.manrope_medium, weight = FontWeight.Medium),
    Font(R.font.manrope_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.manrope_bold, weight = FontWeight.Bold),
    Font(R.font.manrope_extra_bold, weight = FontWeight.ExtraBold)
)

val workSansFontFamily = FontFamily(
    Font(R.font.work_sans_regular, weight = FontWeight.Normal),
    Font(R.font.work_sans_bold, weight = FontWeight.Bold)
)

val robotFontFamily = FontFamily(
    Font(resId = R.font.roboto_regular, weight = FontWeight.Normal),
    Font(resId = R.font.roboto_black, weight = FontWeight.Black),
    Font(resId = R.font.roboto_medium, weight = FontWeight.Medium),
    Font(resId = R.font.roboto_bold, weight = FontWeight.Bold),
    Font(resId = R.font.roboto_light, weight = FontWeight.Light),
    Font(resId = R.font.roboto_thin, weight = FontWeight.Light),
    Font(resId = R.font.roboto_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(resId = R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(resId = R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(resId = R.font.roboto_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(resId = R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
)

val interFontFamily = FontFamily(
    Font(R.font.inter_semi_bold),
    Font(R.font.inter_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(

    bodyLarge = TextStyle(
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)