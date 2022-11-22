
package es.upm.bienestaremocional.app.ui.sleep

import androidx.compose.runtime.*
import androidx.health.connect.client.records.Record
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.sleep.HealthConnectSleep
import es.upm.bienestaremocional.app.data.sleep.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel

class SleepSessionViewModel(val healthConnectSleep: HealthConnectSleep) :
    HealthConnectViewModel()
{
    companion object
    {
        /**
         * Factory class to instance [SleepSessionViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                SleepSessionViewModel(
                    HealthConnectSleep(
                        healthConnectClient = MainApplication.healthConnectClient,
                        healthConnectManager = MainApplication.healthConnectManager
                    )
                )
            }
        }
    }
    //data of viewmodel
    var sleepData: MutableState<List<SleepSessionData>> = mutableStateOf(listOf())

    /**
     * Implements [HealthConnectViewModel.readData] with [SleepSessionData] data
     */
    fun readSleepData()
    {
        @Suppress("UNCHECKED_CAST")

        //This cast can sucess because SleepSessionData implements HealthConnectDataClass
        super.readData(healthConnectSource = healthConnectSleep,
            data = sleepData as MutableState<List<Record>>)
    }
}

