package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.OxygenSaturation
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class OxygenSaturationViewModel(private val oxygenSaturation: OxygenSaturation) :
    HealthConnectViewModel<OxygenSaturationRecord>()
{
    companion object
    {
        /**
         * Factory to instance [OxygenSaturationViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                OxygenSaturationViewModel(
                    OxygenSaturation(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(oxygenSaturation,OxygenSaturation.generateDummyData())
        readData(oxygenSaturation)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<OxygenSaturationRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(oxygenSaturation)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = oxygenSaturation.readPermissions + oxygenSaturation.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}