
package es.upm.bienestaremocional.app.ui.sleep

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.upm.bienestaremocional.app.data.sleep.HealthConnectSleep
import es.upm.bienestaremocional.app.data.sleep.SleepSessionData
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectDataClass
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel

class SleepSessionViewModel(val healthConnectSleep: HealthConnectSleep) :
    HealthConnectViewModel()
{
    var sessionsList: MutableState<List<SleepSessionData>> = mutableStateOf(listOf())

    fun readSleepData()
    {
        @Suppress("UNCHECKED_CAST")

        //This cast can sucess because SleepSessionData implements HealthConnectDataClass
        super.readData(healthConnectSource = healthConnectSleep,
            data = sessionsList as MutableState<List<HealthConnectDataClass>>)
    }
}

//this class is used to init SleepSessionViewModel
class SleepSessionViewModelFactory(private val healthConnectSleep: HealthConnectSleep) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(SleepSessionViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return SleepSessionViewModel(healthConnectSleep) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
