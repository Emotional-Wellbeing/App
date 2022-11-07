package es.upm.bienestaremocional.app.ui.sleep

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.permission.HealthPermission
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upm.bienestaremocional.app.ui.sleep.component.SleepSessionRow
import es.upm.bienestaremocional.app.data.sleep.HealthConnectSleep
import es.upm.bienestaremocional.app.data.sleep.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import java.util.*

/**
 * Debug screen to visualize sleep data from health connect
 */
@Composable
private fun DrawSleepScreen(viewModel: SleepSessionViewModel,
                            sessionList : List<SleepSessionData>,
                            onPermissionsResult: () -> Unit = {},
                            onRequestPermissions: (Set<HealthPermission>) -> Unit = {},
                            onError: (Throwable?) -> Unit = {})
{
    val uiState = viewModel.uiState
    // Remember the last error ID, such that it is possible to avoid re-launching the error
    // notification for the same error when the screen is recomposed, or configuration changes etc.
    val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

    LaunchedEffect(uiState) {
        // If the initial data load has not taken place, attempt to load the data.
        if (uiState is HealthConnectViewModel.UiState.Uninitialized)
            onPermissionsResult()

        // The [SleepSessionViewModel.UiState] provides details of whether the last action was a
        // success or resulted in an error. Where an error occurred, for example in reading and
        // writing to Health Connect, the user is notified, and where the error is one that can be
        // recovered from, an attempt to do so is made.
        if (uiState is HealthConnectViewModel.UiState.Error && errorId.value != uiState.uuid)
        {
            onError(uiState.exception)
            errorId.value = uiState.uuid
        }
    }

    if (uiState != HealthConnectViewModel.UiState.Uninitialized)
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            if (uiState == HealthConnectViewModel.UiState.Success)
            {
                items(sessionList) { session -> SleepSessionRow(session) }
            }
            else if (uiState == HealthConnectViewModel.UiState.NotEnoughPermissions)
            {
                item {
                    Button(onClick = {onRequestPermissions(viewModel.healthConnectSleep.permissions)})
                    {
                        Text(text = "Solicita permisos")
                    }
                }
            }
        }
    }
}

@Composable
fun SleepScreen(healthConnectSleep: HealthConnectSleep, onError: (Throwable?) -> Unit = {})
{
    //get viewmodel to access sleep data
    val viewModel: SleepSessionViewModel = viewModel(
        factory = SleepSessionViewModelFactory(healthConnectSleep = healthConnectSleep)
    )

    val sessionList by viewModel.sessionsList
    val onPermissionsResult = {viewModel.readSleepData()}
    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionLauncher)
        { onPermissionsResult() }

    DrawSleepScreen(
        viewModel = viewModel,
        sessionList = sessionList,
        onPermissionsResult = onPermissionsResult,
        onRequestPermissions = { values -> permissionsLauncher.launch(values)},
        onError = onError)
}
