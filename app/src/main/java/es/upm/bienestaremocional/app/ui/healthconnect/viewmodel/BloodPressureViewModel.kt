package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BloodPressureRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodPressure
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import javax.inject.Inject

@HiltViewModel
class BloodPressureViewModel @Inject constructor(
    private val bloodPressure: BloodPressure
): HealthConnectViewModel<BloodPressureRecord>()
{
    private fun writeAndReadDummyData()
    {
        writeData(bloodPressure,BloodPressure.generateDummyData())
        readData(bloodPressure)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<BloodPressureRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(bloodPressure)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = bloodPressure.readPermissions + bloodPressure.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}