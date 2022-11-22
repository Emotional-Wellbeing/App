package es.upm.bienestaremocional.app.ui.heartrate

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.heartrate.HealthConnectHeartRate
import es.upm.bienestaremocional.app.data.heartrate.generateDummyData
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import kotlinx.coroutines.launch

class HeartRateViewModel(val healthConnectHeartRate: HealthConnectHeartRate) :
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
                    HealthConnectHeartRate(
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
        //This cast can sucess because HealthConnectHeartRate implements HealthConnectDataClass
        super.readData(
            healthConnectSource = healthConnectHeartRate,
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
        writeHeartRateData(generateDummyData())
    }

    /**
     * Write data using [HealthConnectHeartRate.writeSource]
     */
    private fun writeHeartRateData(data: List<Record>)
    {
        viewModelScope.launch {
            if (healthConnectHeartRate.writePermissionsCheck())
                healthConnectHeartRate.writeSource(data)
        }
    }
}
