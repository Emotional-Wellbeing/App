package es.upm.bienestaremocional.ui.screen.sleep

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.permission.HealthPermission
import es.upm.bienestaremocional.data.healthconnect.CommonViewModel
import es.upm.bienestaremocional.data.types.SleepSessionData
import es.upm.bienestaremocional.ui.component.sleep.SleepSessionRow
import java.util.*

/**
 * Debug screen to visualize sleep data from health connect
 */
@Composable
fun SleepScreen(
    permissions: Set<HealthPermission>,
    uiState: CommonViewModel.UiState,
    sessionsList: List<SleepSessionData>,
    onPermissionsResult: () -> Unit = {},
    onRequestPermissions: (Set<HealthPermission>) -> Unit = {},
    onError: (Throwable?) -> Unit = {},
)
{
    // Remember the last error ID, such that it is possible to avoid re-launching the error
    // notification for the same error when the screen is recomposed, or configuration changes etc.
    val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

    LaunchedEffect(uiState) {
        // If the initial data load has not taken place, attempt to load the data.
        if (uiState is CommonViewModel.UiState.Uninitialized)
            onPermissionsResult()


        // The [SleepSessionViewModel.UiState] provides details of whether the last action was a
        // success or resulted in an error. Where an error occurred, for example in reading and
        // writing to Health Connect, the user is notified, and where the error is one that can be
        // recovered from, an attempt to do so is made.
        if (uiState is CommonViewModel.UiState.Error && errorId.value != uiState.uuid)
        {
            onError(uiState.exception)
            errorId.value = uiState.uuid
        }
    }

    if (uiState != CommonViewModel.UiState.Uninitialized)
    {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            if (uiState == CommonViewModel.UiState.Success)
            {
                items(sessionsList) { session -> SleepSessionRow(session) }
            }
            else if (uiState == CommonViewModel.UiState.NotEnoughPermissions)
            {
                item {
                    Button(onClick = {onRequestPermissions(permissions)})
                    {
                        Text(text = "Solicita permisos")
                    }
                }
            }
        }
    }



    /*onPermissionsResult()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        if(permissionsGranted)
        {
            items(sessionsList) { session -> SleepSessionRow(session) }
        }
        else
        {
            item {
                Button(onClick = {onRequestPermissions(permissions)})
                {
                    Text(text = "Solicita permisos")
                }
            }
        }
    }*/

    /*LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!permissionsGranted)
            {
                item {
                    Button(
                        onClick = {  }
                    ) {
                        Text(text = "Solicita permisos")
                    }
                }
            } else {
                items(sessionsList) { session ->
                    SleepSessionRow(session)
                }
            }
        }*/
}
