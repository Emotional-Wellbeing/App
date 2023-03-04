package es.upm.bienestaremocional.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.window.layout.WindowMetricsCalculator
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.app.data.alarm.AlarmScheduler
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.core.ui.responsive.computeWindowSize
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @Inject
    lateinit var appSettings: AppSettings
    @Inject
    lateinit var scheduler: AlarmScheduler

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val windowSize = computeWindowSize(
            windowMetricsCalculator = WindowMetricsCalculator.getOrCreate(),
            activity = this,
            displayMetrics = applicationContext.resources.displayMetrics)

        MainApplication.windowSize = windowSize

        setContent {
            BienestarEmocionalApp(
                appSettings = appSettings,
                scheduler = scheduler
            )
        }
    }
}
