package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.Vo2MaxRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.Vo2Max
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class Vo2MaxViewModel(private val vo2Max: Vo2Max) : HealthConnectViewModel<Vo2MaxRecord>()
{
    companion object
    {
        /**
         * Factory to instance [Vo2MaxViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                Vo2MaxViewModel(
                    Vo2Max(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(vo2Max,Vo2Max.generateDummyData())
        readData(vo2Max)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<Vo2MaxRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(vo2Max)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = vo2Max.readPermissions + vo2Max.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}