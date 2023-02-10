package es.upm.bienestaremocional.app.ui.component.animation

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
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
 * Display Lottie animation
 * @param rawRes: element to display. Must be an element from R.raw
 * @param modifier: Modifier to alter this element
 * @param animationLoop: Indicate if the animation should be animate repeatedly or once
 */
@Composable
fun DisplayLottieAnimation(@RawRes rawRes: Int, modifier: Modifier = Modifier, animationLoop : Boolean = true)
{
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(rawRes))
    LottieAnimation(
        composition = composition,
        modifier = modifier,
        iterations = if (animationLoop) LottieConstants.IterateForever else 1
    )
}

@Preview(showBackground = true)
@Composable
fun DisplayLottieAnimationPreview()
{
    BienestarEmocionalTheme {
        Surface {
            DisplayLottieAnimation(
                rawRes = R.raw.mental_wellbeing,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayLottieAnimationDarkModePreview()
{
    BienestarEmocionalTheme(darkTheme = true) {
        Surface {
            DisplayLottieAnimation(
                rawRes = R.raw.mental_wellbeing,
                modifier = Modifier.fillMaxSize())
        }
    }
}