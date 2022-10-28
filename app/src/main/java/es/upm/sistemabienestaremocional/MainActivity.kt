package es.upm.sistemabienestaremocional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import es.upm.sistemabienestaremocional.healthconnect.HealthConnectManager

class MainActivity : ComponentActivity()
{
    private val healthConnectManager: HealthConnectManager by lazy { HealthConnectManager(this) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent {
            SistemaBienestarEmocionalApp(healthConnectManager = healthConnectManager)
        }
    }
}
