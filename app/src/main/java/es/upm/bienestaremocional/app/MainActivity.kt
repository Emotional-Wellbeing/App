package es.upm.bienestaremocional.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.window.layout.WindowMetricsCalculator
import es.upm.bienestaremocional.core.ui.responsive.computeWindowSize

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val windowSize = computeWindowSize(
                windowMetricsCalculator = WindowMetricsCalculator.getOrCreate(),
                activity = this,
                displayMetrics = applicationContext.resources.displayMetrics)

        setContent {
            BienestarEmocionalApp(
                appSettings = MainApplication.appSettings,
                windowSize = windowSize,
                healthConnectAvailability = MainApplication.healthConnectManager.availability
            )
        }
    }
}
