package com.developing.charityapplication.presentation.view.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.developing.charityapplication.R

val NunitoSans = FontFamily(
    Font(R.font.nunitosans_10pt_light, FontWeight.Light),
    Font(R.font.nunitosans_10pt_regular, FontWeight.Normal),
    Font(R.font.nunitosans_10pt_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunitosans_10pt_semibold, FontWeight.Normal)
)

val AppTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 32.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.em
    ),
    headlineMedium = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 24.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.em
    ),
    headlineSmall = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 20.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.em
    ),
    titleMedium = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.em
    ),
    bodyMedium = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.em
    ),
    labelMedium = TextStyle(
        fontFamily = NunitoSans,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 1.em
    )
)