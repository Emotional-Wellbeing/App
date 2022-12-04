package es.upm.bienestaremocional.app.ui.healthconnect.viewmodel

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.RestingHeartRate
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData

class RestingHeartRateViewModel(private val restingHeartRate: RestingHeartRate) :
    HealthConnectViewModel()
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
    private var rhrData : MutableState<List<RestingHeartRateRecord>> = mutableStateOf(listOf())

    /**
     * Read data calling [HealthConnectViewModel.readData]
     */
    private fun readData()
    {
        @Suppress("UNCHECKED_CAST")
        super.readData(healthConnectSource = restingHeartRate,
            data = rhrData as MutableState<List<Record>>)
    }

    private fun writeData(data: List<Record>)
    {
        super.writeData(healthConnectSource = restingHeartRate, data = data)
    }

    private fun writeData()
    {
        writeData(RestingHeartRate.generateDummyData())
    }

    private fun writeAndReadDummyData()
    {
        writeData()
        readData()
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by rhrData
        val onPermissionsResult = {readData()}

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