package es.upm.bienestaremocional.ui.theme

import androidx.compose.material3.Typography

// Set of Material typography styles to start with

private val defaultTypography = Typography()

val Typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = Quicksand),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = Quicksand),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = Quicksand),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = Quicksand),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = Quicksand),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = Quicksand),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = Quicksand),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = Quicksand),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = Quicksand),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = Quicksand),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = Quicksand),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = Quicksand),
    labelLarge = defaultTypography.labelLarge.copy(fontFamily = Quicksand),
    labelMedium = defaultTypography.labelMedium.copy(fontFamily = Quicksand),
    labelSmall = defaultTypography.labelSmall.copy(fontFamily = Quicksand),
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
