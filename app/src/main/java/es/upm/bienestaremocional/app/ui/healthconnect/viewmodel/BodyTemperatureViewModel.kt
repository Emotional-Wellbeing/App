package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.BodyTemperature
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class BodyTemperatureViewModel(private val bodyTemperature: BodyTemperature) :
    HealthConnectViewModel<BodyTemperatureRecord>()
{
    companion object
    {
        /**
         * Factory to instance [BodyTemperatureViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                BodyTemperatureViewModel(
                    BodyTemperature(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(bodyTemperature,BodyTemperature.generateDummyData())
        readData(bodyTemperature)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<BodyTemperatureRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(bodyTemperature)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = bodyTemperature.readPermissions + bodyTemperature.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}