package es.upm.bienestaremocional.core.ui.responsive

import android.app.Activity
import android.util.DisplayMetrics
import androidx.window.layout.WindowMetricsCalculator

/**
 * Extracted from https://developer.android.com/guide/topics/large-screens/support-different-screen-sizes#kotlin
 */
fun computeWindowSizeClasses(windowMetricsCalculator: WindowMetricsCalculator,
                             activity: Activity,
                             displayMetrics : DisplayMetrics): WindowSizeClass
{
    val metrics = windowMetricsCalculator.computeCurrentWindowMetrics(activity)

    val widthDp = metrics.bounds.width() / displayMetrics.density

    val widthWindowSizeClass = when
    {
        widthDp < 600f -> WindowSizeClass.COMPACT
        widthDp < 840f -> WindowSizeClass.MEDIUM
        else -> WindowSizeClass.EXPANDED
    }

    return widthWindowSizeClass
}