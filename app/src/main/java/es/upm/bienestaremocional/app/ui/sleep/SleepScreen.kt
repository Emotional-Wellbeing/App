package es.upm.bienestaremocional.app.ui.sleep

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.types.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.UiState
import es.upm.bienestaremocional.core.ui.component.DrawHealthConnectScreen
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Debug screen to visualize sleep data from health connect
 */
@Suppress("UNCHECKED_CAST")
@Composable
private fun DrawSleepScreen(viewModelData: ViewModelData,
                            onError: (Throwable?) -> Unit = {})
{
    DrawHealthConnectScreen(viewModelData = viewModelData,
        onEmptyData = { Text(text = "Lista vacía")},
        onDisplayData = { sleepSeries(viewModelData.data as List<SleepSessionData>)},
        onError = onError)
}

@Composable
fun SleepScreen(onError: (Throwable?) -> Unit = {})
{
    val viewModel: SleepSessionViewModel = viewModel(factory = SleepSessionViewModel.Factory)
    DrawSleepScreen(viewModelData = viewModel.getViewModelData(), onError = onError)
}


@Preview(showBackground = true)
@Composable
fun SleepScreenPreview()
{
    val viewModelData = ViewModelData(
        data = Sleep.generateDummyData(),
        uiState = UiState.Success,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {})
    BienestarEmocionalTheme()
    {
        DrawSleepScreen(viewModelData = viewModelData, onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SleepScreenPreviewDarkMode()
{
    val viewModelData = ViewModelData(
        data = Sleep.generateDummyData(),
        uiState = UiState.Success,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {})
    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawSleepScreen(viewModelData = viewModelData, onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SleepScreenNotEnoughPermissionsPreview()
{
    val viewModelData = ViewModelData(
        data = listOf(),
        uiState = UiState.NotEnoughPermissions,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {})
    BienestarEmocionalTheme()
    {
        DrawSleepScreen(viewModelData = viewModelData, onError = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SleepScreenNotEnoughPermissionsPreviewDarkMode()
{
    val viewModelData = ViewModelData(
        data = listOf(),
        uiState = UiState.NotEnoughPermissions,
        permissions = setOf(),
        onPermissionsResult = { },
        onRequestPermissions = {})

    BienestarEmocionalTheme(darkTheme = true)
    {
        DrawSleepScreen(viewModelData = viewModelData, onError = {})
    }
}