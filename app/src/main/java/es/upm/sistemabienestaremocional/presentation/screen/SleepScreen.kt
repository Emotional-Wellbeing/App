package es.upm.sistemabienestaremocional.presentation.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.health.connect.client.permission.HealthPermission
import es.upm.sistemabienestaremocional.data.SleepSessionData
import es.upm.sistemabienestaremocional.presentation.component.SleepSessionRow

/**
 * Shows a week's worth of sleep data.
 */
@Composable
fun SleepScreen(
    permissions: Set<HealthPermission>,
    permissionsGranted: Boolean,
    sessionsList: List<SleepSessionData>,
    onPermissionsResult: () -> Unit = {},
    onPermissionsLaunch: (Set<HealthPermission>) -> Unit = {}
)
{
    onPermissionsResult()

    LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!permissionsGranted) {
                item {
                    Button(
                        onClick = { onPermissionsLaunch(permissions) }
                    ) {
                        Text(text = "Solicita permisos")
                    }
                }
            } else {
                items(sessionsList) { session ->
                    SleepSessionRow(session)
                }
            }
        }
}
