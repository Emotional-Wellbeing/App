package es.upm.bienestaremocional.ui.component.onboarding

import es.upm.bienestaremocional.R

/**
 * Contains a list of all slides of OnboardingScreen
 * @see HorizontalPagerContent
 * @see OnboardingContent
 */

object OnboardingContent {
    val content = listOf(
        HorizontalPagerContent(
            animation = R.raw.mental_wellbeing,
            title = R.string.onboarding_welcome_title,
            content = R.string.onboarding_welcome_description,
            animationLoop = true
        ),
        HorizontalPagerContent(
            animation = R.raw.mental_health_awareness,
            title = R.string.onboarding_features_title,
            content = R.string.onboarding_features_description,
            animationLoop = true
        ),
        HorizontalPagerContent(
            animation = R.raw.health_checkup,
            title = R.string.onboarding_privacy_title,
            content = R.string.onboarding_privacy_description,
            animationLoop = true
        ),
        HorizontalPagerContent(
            animation = R.raw.how_it_works,
            title = R.string.onboarding_community_title,
            content = R.string.onboarding_community_description,
            animationLoop = true
        ),
    )
}