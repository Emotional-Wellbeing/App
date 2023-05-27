package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.FloorsClimbedRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class FloorsClimbedViewModel @Inject constructor(
    private val floorsClimbed: FloorsClimbed
): HealthConnectViewModel<FloorsClimbedRecord>()
{

    private fun writeAndReadDummyData()
    {
        writeData(floorsClimbed, FloorsClimbed.generateDummyData())
        readData(floorsClimbed)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<FloorsClimbedRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(floorsClimbed)}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = floorsClimbed.readPermissions + floorsClimbed.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
