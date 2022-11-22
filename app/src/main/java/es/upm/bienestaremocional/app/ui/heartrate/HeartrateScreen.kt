package es.upm.bienestaremocional.app.ui.heartrate

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upm.bienestaremocional.app.data.heartrate.*
import es.upm.bienestaremocional.app.ui.heartrate.component.heartRateSeries
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.UiState
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import java.util.*

@Composable
private fun HeartRateScreen(heartRateData : List<HeartRateRecord>,
                        uiState: UiState,
                        permissions: Set<HealthPermission>,
                        onPermissionsResult: () -> Unit = {},
                        onWrite: () -> Unit = {},
                        onRequestPermissions: (Set<HealthPermission>) -> Unit = {},
                        onError: (Throwable?) -> Unit = {})
{
    // Remember the last error ID, such that it is possible to avoid re-launching the error
    // notification for the same error when the screen is recomposed, or configuration changes etc.
    val errorId = rememberSaveable { mutableStateOf(UUID.randomUUID()) }

    LaunchedEffect(uiState)
    {
        // If the initial data load has not taken place, attempt to load the data.
        if (uiState is UiState.Uninitialized)
            onPermissionsResult()

        // The UiState provides details of whether the last action was a
        // success or resulted in an error. Where an error occurred, for example in reading and
        // writing to Health Connect, the user is notified, and where the error is one that can be
        // recovered from, an attempt to do so is made.
        if (uiState is UiState.Error && errorId.value != uiState.uuid)
        {
            onError(uiState.exception)
            errorId.value = uiState.uuid
        }
    }

    if (uiState != UiState.Uninitialized)
    {
        Surface {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                if (uiState == UiState.Success)
                {
                    if (heartRateData.isEmpty())
                    {
                        item {
                            Button(onClick = { onWrite() })
                            {
                                Text("Generar datos")
                            }
                        }
                    }
                    else
                        heartRateSeries(heartRateData)
                }
                else if (uiState == UiState.NotEnoughPermissions)
                {
                    item {
                        Button(onClick = { onRequestPermissions(permissions) })
                        {
                            Text(text = "Solicita permisos")
                        }
                    }
                }
            }
        }
    }
}

/**
 * Debug screen to visualize heart rate data
 * @see HeartRateViewModel
 * @see [HealthConnectHeartRate]
 */
@Composable
fun HeartRateScreenWrapper(onError: (Throwable?) -> Unit = {})
{
    val viewModel: HeartRateViewModel = viewModel(factory = HeartRateViewModel.Factory)
    // variables to start screen
    val heartRateData by viewModel.heartRateData
    val uiState = viewModel.uiState
    val permissions = viewModel.healthConnectHeartRate.readPermissions +
            viewModel.healthConnectHeartRate.writePermissions
    val onPermissionsResult = {viewModel.readHeartRateData()}
    val onWrite = {viewModel.writeAndReadDummyData()}

    //launcher is a special case
    val permissionsLauncher =
        rememberLauncherForActivityResult(viewModel.permissionLauncher)
        { onPermissionsResult() }

    HeartRateScreen(
        heartRateData = heartRateData,
        uiState = uiState,
        permissions = permissions,
        onPermissionsResult = onPermissionsResult,
        onWrite = onWrite,
        onRequestPermissions = { values -> permissionsLauncher.launch(values)},
        onError = onError)
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenPreview()
{
    BienestarEmocionalTheme()
    {
        HeartRateScreen(
            heartRateData = generateDummyData(),
            uiState = UiState.Success,
            permissions = setOf(),
            onPermissionsResult = { },
            onWrite = {},
            onRequestPermissions = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HeartRateScreen(
            heartRateData = generateDummyData(),
            uiState = UiState.Success,
            permissions = setOf(),
            onPermissionsResult = { },
            onWrite = {},
            onRequestPermissions = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNoDataPreview()
{
    BienestarEmocionalTheme()
    {
        HeartRateScreen(
            heartRateData = listOf(),
            uiState = UiState.Success,
            permissions = setOf(),
            onPermissionsResult = { },
            onWrite = {},
            onRequestPermissions = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNoDataPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HeartRateScreen(
            heartRateData = listOf(),
            uiState = UiState.Success,
            permissions = setOf(),
            onPermissionsResult = { },
            onWrite = {},
            onRequestPermissions = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNotEnoughPermissionsPreview()
{
    BienestarEmocionalTheme()
    {
        HeartRateScreen(
            heartRateData = listOf(),
            uiState = UiState.NotEnoughPermissions,
            permissions = setOf(),
            onPermissionsResult = { },
            onWrite = {},
            onRequestPermissions = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNotEnoughPermissionsPreviewDarkMode()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        HeartRateScreen(
            heartRateData = listOf(),
            uiState = UiState.NotEnoughPermissions,
            permissions = setOf(),
            onPermissionsResult = { },
            onWrite = {},
            onRequestPermissions = {},
            onError = {})
    }
}