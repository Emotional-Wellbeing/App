package es.upm.bienestaremocional.core.ui.responsive

import android.app.Activity
import android.util.DisplayMetrics
import androidx.window.layout.WindowMetricsCalculator

/**
 * Stores the screen's type to implement responsive layouts
 */
enum class WindowSize { COMPACT, MEDIUM, EXPANDED }

/**
 * Computes window size according for their width. Threshold values are extracted from
 * https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#kotlin
 */
fun computeWindowSize(windowMetricsCalculator: WindowMetricsCalculator,
                      activity: Activity,
                      displayMetrics : DisplayMetrics): WindowSize
{
    val metrics = windowMetricsCalculator.computeCurrentWindowMetrics(activity)

    val widthDp = metrics.bounds.width() / displayMetrics.density

    val widthWindowSizeClass = when
    {
        widthDp < 600f -> WindowSize.COMPACT
        widthDp < 840f -> WindowSize.MEDIUM
        else -> WindowSize.EXPANDED
    }

    return widthWindowSizeClass
}