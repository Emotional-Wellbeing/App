package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.HeartRateRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class HeartRateViewModel(private val heartRate: HeartRate) :
    HealthConnectViewModel<HeartRateRecord>()
{
    companion object
    {
        /**
         * Factory class to instance [HeartRateViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                HeartRateViewModel(
                    HeartRate(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(heartRate, HeartRate.generateDummyData())
        readData(heartRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<HeartRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(heartRate)}

        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = heartRate.readPermissions + heartRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}
