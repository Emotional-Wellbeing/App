package es.upm.bienestaremocional.app.ui.component.onboarding

import androidx.annotation.RawRes

/**
 * Contains data used for sliders
 * @param animation: Resource (part of R.raw) that should be animated
 * @param animationLoop: Indicate if the animation should be animate repeatedly or once
 * @param title: Resource (part of R.string) that contains the headline of the screen
 * @param content: Resource (part of R.string) that contains the description of the screen
 */
data class HorizontalPagerContent(
    @RawRes val animation : Int,
    val animationLoop: Boolean,
    val title: Int,
    val content: Int
)