package es.upm.bienestaremocional.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.worker.WorkAdministrator
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    @Inject
    lateinit var appSettings: AppSettings
    @Inject
    lateinit var scheduler: WorkAdministrator

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        setContent {
            BienestarEmocionalApp(
                appSettings = appSettings,
                scheduler = scheduler,
                activity = this
            )
        }
    }
}
