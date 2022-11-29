package es.upm.bienestaremocional.app.ui.healthconnect.heartrate

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
                                onError: (Throwable?) -> Unit = {})
{
    DrawHealthConnectScreen(viewModelData = viewModelData,
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
    DrawHeartRateScreen(viewModelData = viewModel.getViewModelData(), onError = onError)
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
        onWrite = {}
    )
    BienestarEmocionalTheme()
    {
        DrawHeartRateScreen(viewModelData = viewModelData, onError = {})
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
        onWrite = {}
    )
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawHeartRateScreen(viewModelData = viewModelData, onError = {})
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
        onWrite = {}
    )
    BienestarEmocionalTheme()
    {
        DrawHeartRateScreen(viewModelData = viewModelData, onError = {})
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
        onWrite = {}
    )
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawHeartRateScreen(viewModelData = viewModelData, onError = {})
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
        onWrite = {}
    )
    BienestarEmocionalTheme()
    {
        DrawHeartRateScreen(viewModelData = viewModelData, onError = {})
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
        onWrite = {}
    )
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawHeartRateScreen(viewModelData = viewModelData, onError = {})
    }
}