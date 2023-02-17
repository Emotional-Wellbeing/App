package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.DistanceRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class DistanceViewModel @Inject constructor(
    private val distance: Distance
): HealthConnectViewModel<DistanceRecord>()
{

    private fun writeAndReadDummyData()
    {
        writeData(distance, Distance.generateDummyData())
        readData(distance)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<DistanceRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(distance)}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = distance.readPermissions + distance.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
