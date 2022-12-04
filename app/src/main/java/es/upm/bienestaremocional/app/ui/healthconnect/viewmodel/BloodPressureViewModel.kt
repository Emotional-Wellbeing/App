package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.BloodPressure
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class BloodPressureViewModel(private val bloodPressure: BloodPressure) :
    HealthConnectViewModel<BloodPressureRecord>()
{
    companion object
    {
        /**
         * Factory to instance [BloodPressureViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                BloodPressureViewModel(
                    BloodPressure(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

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