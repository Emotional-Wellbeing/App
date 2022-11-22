package es.upm.bienestaremocional.app.ui.component.onboarding

import es.upm.bienestaremocional.R

/**
 * Contains a list of all slides of OnboardingScreen
 * @see HorizontalPagerContent
 * @see OnboardingContent
 */

object OnboardingContent
{
    val content = listOf(
        HorizontalPagerContent(
            animation = R.raw.mental_wellbeing,
            animationLoop = true,
            title = R.string.onboarding_welcome_title,
            content = R.string.onboarding_welcome_description
        ),
        HorizontalPagerContent(
            animation = R.raw.mental_health_awareness,
            animationLoop = true,
            title = R.string.onboarding_features_title,
            content = R.string.onboarding_features_description
        ),
        HorizontalPagerContent(
            animation = R.raw.health_checkup,
            animationLoop = true,
            title = R.string.onboarding_privacy_title,
            content = R.string.onboarding_privacy_description
        ),
        HorizontalPagerContent(
            animation = R.raw.how_it_works,
            animationLoop = true,
            title = R.string.onboarding_community_title,
            content = R.string.onboarding_community_description
        ),
    )
}