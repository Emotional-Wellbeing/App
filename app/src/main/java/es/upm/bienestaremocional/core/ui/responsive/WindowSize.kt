package es.upm.bienestaremocional.core.ui.responsive

import androidx.compose.material3.windowsizeclass.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import es.upm.bienestaremocional.app.utils.getActivity

/**
 * Computes [WindowSizeClass] using Material Design 3 Window Size api.
 * This function is a superset of [computeWindowHeightSize] and [computeWindowWidthSize]
 * @return [WindowSizeClass] of the screen
 */
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun computeWindowSizeClass(): WindowSizeClass =
    calculateWindowSizeClass(LocalContext.current.getActivity())

/**
 * Computes [WindowWidthSizeClass] using Material Design 3 Window Size api
 * @return [WindowWidthSizeClass] of the screen
 */
@Composable
fun computeWindowWidthSize(): WindowWidthSizeClass =
    computeWindowSizeClass().widthSizeClass

/**
 * Computes [WindowHeightSizeClass] using Material Design 3 Window Size api
 * @return [WindowHeightSizeClass] of the screen
 */
@Composable
fun computeWindowHeightSize(): WindowHeightSizeClass =
    computeWindowSizeClass().heightSizeClass