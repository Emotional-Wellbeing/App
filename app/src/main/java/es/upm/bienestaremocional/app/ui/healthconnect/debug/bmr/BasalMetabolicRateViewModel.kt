package es.upm.bienestaremocional.app.ui.healthconnect.debug.bmr

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.BasalMetabolicRateRecord
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.BasalMetabolicRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class BasalMetabolicRateViewModel(private val basalMetabolicRate: BasalMetabolicRate) :
    HealthConnectViewModel()
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
    private var bmrData : MutableState<List<BasalMetabolicRateRecord>> = mutableStateOf(listOf())

    /**
     * Read data calling [HealthConnectViewModel.readData]
     */
    private fun readData()
    {
        @Suppress("UNCHECKED_CAST")
        super.readData(healthConnectSource = basalMetabolicRate,
            data = bmrData as MutableState<List<Record>>)
    }

    private fun writeData(data: List<Record>)
    {
        super.writeData(healthConnectSource = basalMetabolicRate, data = data)
    }

    private fun writeData()
    {
        writeData(BasalMetabolicRate.generateDummyData())
    }

    private fun writeAndReadDummyData()
    {
        writeData()
        readData()
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by bmrData
        val onPermissionsResult = {readData()}

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