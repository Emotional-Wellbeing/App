package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.ElevationGainedRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class ElevationGainedViewModel(private val elevationGained: ElevationGained) :
    HealthConnectViewModel<ElevationGainedRecord>()
{
    companion object
    {
        /**
         * Factory to instance [ElevationGainedViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ElevationGainedViewModel(
                    ElevationGained(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(elevationGained,ElevationGained.generateDummyData())
        readData(elevationGained)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<ElevationGainedRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(elevationGained)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = elevationGained.readPermissions + elevationGained.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}