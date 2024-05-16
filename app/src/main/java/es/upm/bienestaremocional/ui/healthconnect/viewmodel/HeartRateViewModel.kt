package es.upm.bienestaremocional.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.HeartRateRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class HeartRateViewModel @Inject constructor(
    private val heartRate: HeartRate
) : HealthConnectViewModel<HeartRateRecord>() {
    private fun writeAndReadDummyData() {
        writeData(heartRate, HeartRate.generateDummyData())
        readData(heartRate)
    }
    @Composable
    override fun getViewModelData(): ViewModelData<HeartRateRecord> {
        val data by elements
        val onPermissionsResult = { readData(heartRate) }

        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = heartRate.readPermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values) },
            onWrite = { writeAndReadDummyData() }
        )
    }
}
