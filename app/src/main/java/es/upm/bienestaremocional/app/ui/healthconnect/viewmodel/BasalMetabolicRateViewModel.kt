package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.BasalMetabolicRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class BasalMetabolicRateViewModel(private val basalMetabolicRate: BasalMetabolicRate) :
    HealthConnectViewModel<BasalMetabolicRateRecord>()
{
    companion object
    {
        /**
         * Factory to instance [BasalMetabolicRateViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                BasalMetabolicRateViewModel(
                    BasalMetabolicRate(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }

    private fun writeAndReadDummyData()
    {
        writeData(basalMetabolicRate,BasalMetabolicRate.generateDummyData())
        readData(basalMetabolicRate)
    }

    @Composable
    override fun getViewModelData(): ViewModelData<BasalMetabolicRateRecord>
    {
        val data by elements
        val onPermissionsResult = {readData(basalMetabolicRate)}

        val launcher = rememberLauncherForActivityResult(contract = permissionLauncher,
            onResult = {onPermissionsResult()})

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = basalMetabolicRate.readPermissions + basalMetabolicRate.writePermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = {values -> launcher.launch(values)},
            onWrite = {writeAndReadDummyData()}
        )
    }
}