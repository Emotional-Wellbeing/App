package es.upm.bienestaremocional.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import es.upm.bienestaremocional.core.healthconnect.data.HealthConnectManager

class MainActivity : ComponentActivity()
{
    private val healthConnectManager: HealthConnectManager by lazy { HealthConnectManager(this) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            BienestarEmocionalApp(healthConnectManager = healthConnectManager)
        }
    }
}
