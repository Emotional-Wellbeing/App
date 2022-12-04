package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.RespiratoryRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class RespiratoryRateViewModel(private val respiratoryRate: RespiratoryRate) :
    HealthConnectViewModel<RespiratoryRateRecord>()
{
    companion object
    {
        /**
         * Factory to instance [RespiratoryRateViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RespiratoryRateViewModel(
                    RespiratoryRate(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(respiratoryRate,RespiratoryRate.generateDummyData())
        readData(respiratoryRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<RespiratoryRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(respiratoryRate)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = respiratoryRate.readPermissions + respiratoryRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}