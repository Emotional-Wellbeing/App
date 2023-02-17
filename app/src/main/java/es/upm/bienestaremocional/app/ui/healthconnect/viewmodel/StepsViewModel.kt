package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.StepsRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    private val steps: Steps
): HealthConnectViewModel<StepsRecord>()
{

    private fun writeAndReadDummyData()
    {
        writeData(steps,Steps.generateDummyData())
        readData(steps)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<StepsRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(steps)}

        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = steps.readPermissions + steps.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}