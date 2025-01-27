package com.cromulent.cartio.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val md_theme_light_primary = Color(0xFF16A34A)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0x6616A34A)
val md_theme_light_onPrimaryContainer = Color(0xFF002107)
val md_theme_light_secondary = Color(0xFF4B5563)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFF3F4F6)
val md_theme_light_onSecondaryContainer = Color(0xFF1F2937)
val md_theme_light_error = Color(0xFFDC2626)
val md_theme_light_errorContainer = Color(0xFFFEE2E2)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1F2937)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1F2937)
val md_theme_light_surfaceVariant = Color(0xFFE8E8EA)
val md_theme_light_onSurfaceVariant = Color(0xFF424940)
val md_theme_light_outline = Color(0xFF9CA3AF)

val md_theme_dark_primary = Color(0xFF4ADE80)
val md_theme_dark_onPrimary = Color(0xFF003910)
val md_theme_dark_primaryContainer = Color(0xFF2A332C)
val md_theme_dark_onPrimaryContainer = Color(0xFF7DFF9B)
val md_theme_dark_secondary = Color(0xFF9CA3AF)
val md_theme_dark_onSecondary = Color(0xFF1E1E1E)
val md_theme_dark_secondaryContainer = Color(0xFF1F2937)
val md_theme_dark_onSecondaryContainer = Color(0xFFD5E3FF)
val md_theme_dark_error = Color(0xFFFF917B)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1C1B1F)
val md_theme_dark_onBackground = Color(0xFFE5E1E6)
val md_theme_dark_surface = Color(0xFF1C1B1F)
val md_theme_dark_onSurface = Color(0xFFE5E1E6)
val md_theme_dark_surfaceVariant = Color(0xFF2C2C2C)
val md_theme_dark_onSurfaceVariant = Color(0xFFC3C8BC)
val md_theme_dark_outline = Color(0xFF8D9286)

@Composable
fun listPageGradient(darkTheme: Boolean = isSystemInDarkTheme()) = Brush.verticalGradient(
    colors = if (darkTheme) {
        listOf(
            Color(0xFF121212),
            Color(0xFF121212),
        )
    } else {
        listOf(
            Color(0xCCF0FDF4),  // Light green with 80% opacity
            Color(0x80F0FDF4),  // Light green with 50% opacity
            Color(0xFFF3F4F6)   // Light gray
        )
    }
)