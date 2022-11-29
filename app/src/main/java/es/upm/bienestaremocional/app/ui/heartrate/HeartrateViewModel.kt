package es.upm.bienestaremocional.app.ui.heartrate

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate

import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import es.upm.bienestaremocional.core.ui.component.ViewModelData
import kotlinx.coroutines.launch

class HeartRateViewModel(val heartRate: HeartRate) :
    HealthConnectViewModel()
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

    //data of viewmodel
    var heartRateData: MutableState<List<HeartRateRecord>> = mutableStateOf(listOf())

    /**
     * Implements [HealthConnectViewModel.readData] with [HeartRateRecord] data
     */
    fun readHeartRateData()
    {
        @Suppress("UNCHECKED_CAST")
        /**
         * This cast can sucess because [HeartRateRecord] implements [Record]
         * */
        super.readData(
            healthConnectSource = heartRate,
            data = heartRateData as MutableState<List<Record>>)
    }

    /**
     * Demo function used to write and read the data to show it
     */
    fun writeAndReadDummyData()
    {
        writeHeartRateDummyData()
        readHeartRateData()
    }

    /**
     * Generate dummy data
     */
    private fun writeHeartRateDummyData()
    {
        writeHeartRateData(HeartRate.generateDummyData())
    }

    /**
     * Write data using [HeartRate.writeSource]
     */
    private fun writeHeartRateData(data: List<Record>)
    {
        viewModelScope.launch {
            if (heartRate.writePermissionsCheck())
                heartRate.writeSource(data)
        }
    }

    @Composable
    override fun getViewModelData(): ViewModelData
    {
        val data by heartRateData
        val onPermissionsResult = {readHeartRateData()}

        //launcher is a special case
        val permissionsLauncher =
            rememberLauncherForActivityResult(permissionLauncher) { onPermissionsResult() }

        return ViewModelData(
            data = data,
            uiState = uiState,
            permissions = heartRate.readPermissions,
            onPermissionsResult = onPermissionsResult,
            onRequestPermissions = { values -> permissionsLauncher.launch(values)},
        )
    }
}
