package es.upm.bienestaremocional.app.ui.component.onboarding

import androidx.annotation.RawRes
import androidx.annotation.StringRes

/**
 * Contains data used for sliders
 * @param animation: RawResource that should be animated
 * @param title: StringResource (part of R.string) that contains the headline of the screen
 * @param content: StringResource (part of R.string) that contains the description of the screen
 * @param animationLoop: Indicate if the animation should be animate repeatedly or once
 */
data class HorizontalPagerContent(
    @RawRes val animation : Int,
    @StringRes val title: Int,
    @StringRes val content: Int,
    val animationLoop: Boolean,
)