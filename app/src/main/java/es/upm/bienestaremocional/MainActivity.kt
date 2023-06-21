package es.upm.bienestaremocional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.data.settings.ThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : ComponentActivity()
{
    @Inject
    lateinit var appSettings: AppSettings

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        lateinit var darkTheme : ThemeMode
        var dynamicColors by Delegates.notNull<Boolean>()

        val job = CoroutineScope(Dispatchers.Default).launch {
            darkTheme = appSettings.getTheme().first()
            dynamicColors  = appSettings.getDynamicColors().first()
        }

        CoroutineScope(Dispatchers.Main).launch {
            job.join()
            setContent {
                BienestarEmocionalApp(
                    darkTheme = darkTheme,
                    dynamicColors = dynamicColors,
                )
            }
        }
    }
}
