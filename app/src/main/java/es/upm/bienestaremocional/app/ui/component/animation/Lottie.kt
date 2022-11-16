package es.upm.bienestaremocional.app.ui.component.animation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Takes a resource raw id and display the animation
 * @param rawRes: element to display
 * @param modifier: Modifier to alter this element
 */
@Composable
fun DisplayLottieAnimation(rawRes: Int, modifier: Modifier = Modifier, animationLoop : Boolean = true)
{
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(rawRes))
    LottieAnimation(
        composition = composition,
        iterations = if (animationLoop) LottieConstants.IterateForever else 1,
        modifier = modifier
    )
}

@Preview
@Composable
fun DisplayLottieAnimationPreview()
{
    BienestarEmocionalTheme {
        DisplayLottieAnimation(rawRes = R.raw.mental_wellbeing,
            modifier = Modifier.fillMaxSize())
    }
}