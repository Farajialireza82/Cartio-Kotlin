package com.cromulent.cartio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cromulent.cartio.R

val yekanFont = FontFamily(
    Font(R.font.yekan_light, FontWeight.Light),
    Font(R.font.yekan_regular, FontWeight.Normal),
//    Font(R.font.yekan_x_medium, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.yekan_medium, FontWeight.Medium),
    Font(R.font.yekan_bold, FontWeight.Bold)
)

val Typography = Typography(
    titleSmall = TextStyle(
        fontFamily = yekanFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light
    ),
    headlineLarge = TextStyle(
        fontFamily = yekanFont,
        fontSize = 36.sp,
        fontWeight = FontWeight.Medium
    ),
    titleLarge = TextStyle(
        fontFamily = yekanFont,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium
    ),
    bodyLarge = TextStyle(
        fontFamily = yekanFont,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    bodyMedium = TextStyle(
        fontFamily = yekanFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    ),
    labelLarge = TextStyle(
        fontFamily = yekanFont,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium
    )
)

