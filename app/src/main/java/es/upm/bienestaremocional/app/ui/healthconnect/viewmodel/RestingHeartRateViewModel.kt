package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.RestingHeartRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class RestingHeartRateViewModel(private val restingHeartRate: RestingHeartRate) :
    HealthConnectViewModel<RestingHeartRateRecord>()
{
    companion object
    {
        /**
         * Factory to instance [RestingHeartRateViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                RestingHeartRateViewModel(
                    RestingHeartRate(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(restingHeartRate,RestingHeartRate.generateDummyData())
        readData(restingHeartRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<RestingHeartRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(restingHeartRate)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = restingHeartRate.readPermissions + restingHeartRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}