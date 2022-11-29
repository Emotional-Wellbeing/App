package es.upm.bienestaremocional.app.ui.heartrate

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.health.connect.client.records.HeartRateRecord
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.UiState
import es.upm.bienestaremocional.core.ui.component.DrawHealthConnectScreen
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Suppress("UNCHECKED_CAST")
@Composable
private fun DrawHeartRateScreen(viewModelData: ViewModelData,
                                onWrite : () -> Unit,
                                onError: (Throwable?) -> Unit = {})
{
    DrawHealthConnectScreen(viewModelData = viewModelData,
        onEmptyData = {
            Button(onClick = { onWrite() })
            {
                Text("Generar datos")
            }
        },
        onDisplayData = { heartRateSeries(viewModelData.data as List<HeartRateRecord>)},
        onError = onError)
}

/**
 * Debug screen to visualize heart rate data
 * @see HeartRateViewModel
 * @see [HeartRate]
 */
@Composable
fun HeartRateScreen(onError: (Throwable?) -> Unit = {})
{
    val viewModel: HeartRateViewModel = viewModel(factory = HeartRateViewModel.Factory)
    DrawHeartRateScreen(viewModelData = viewModel.getViewModelData(),
        onWrite = {viewModel.writeAndReadDummyData()},
        onError = onError)

}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenPreview()
{
    val viewModelData = ViewModelData(
        data = HeartRate.generateDummyData(),
        uiState = UiState.Success,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {},
    )
    BienestarEmocionalTheme()
    {
        DrawHeartRateScreen(
            viewModelData = viewModelData,
            onWrite = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenPreviewDarkMode()
{
    val viewModelData = ViewModelData(
        data = HeartRate.generateDummyData(),
        uiState = UiState.Success,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {},
    )
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawHeartRateScreen(
            viewModelData = viewModelData,
            onWrite = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNoDataPreview()
{
    val viewModelData = ViewModelData(
        data = listOf(),
        uiState = UiState.Success,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {},
    )
    BienestarEmocionalTheme()
    {
        DrawHeartRateScreen(
            viewModelData = viewModelData,
            onWrite = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNoDataPreviewDarkMode()
{
    val viewModelData = ViewModelData(
        data = listOf(),
        uiState = UiState.Success,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {},
    )
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawHeartRateScreen(
            viewModelData = viewModelData,
            onWrite = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNotEnoughPermissionsPreview()
{
    val viewModelData = ViewModelData(
        data = listOf(),
        uiState = UiState.NotEnoughPermissions,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {},
    )
    BienestarEmocionalTheme()
    {
        DrawHeartRateScreen(
            viewModelData = viewModelData,
            onWrite = {},
            onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun HealthRateScreenNotEnoughPermissionsPreviewDarkMode()
{
    val viewModelData = ViewModelData(
        data = listOf(),
        uiState = UiState.NotEnoughPermissions,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {},
    )
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawHeartRateScreen(
            viewModelData = viewModelData,
            onWrite = {},
            onError = {})
    }
}