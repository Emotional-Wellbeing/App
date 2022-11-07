package es.upm.bienestaremocional.app.ui.heartrate

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.upm.bienestaremocional.app.data.heartrate.HealthConnectHeartrate
import es.upm.bienestaremocional.app.data.heartrate.HeartrateData
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectDataClass
import es.upm.bienestaremocional.core.extraction.healthconnect.ui.HealthConnectViewModel
import kotlinx.coroutines.launch

class HeartrateViewModel(val healthConnectHeartrate: HealthConnectHeartrate) :
    HealthConnectViewModel()
{
    var hrList: MutableState<List<HeartrateData>> = mutableStateOf(listOf())

    fun readHeartrateData()
    {
        @Suppress("UNCHECKED_CAST")
        //This cast can sucess because HealthConnectHeartrate implements HealthConnectDataClass
        super.readData(healthConnectSource = healthConnectHeartrate,
            data = hrList as MutableState<List<HealthConnectDataClass>>
        )
    }

    fun generateSamples()
    {
        generateHeartrateData()
        readHeartrateData()
    }

    private fun generateHeartrateData()
    {
        viewModelScope.launch {
            if (healthConnectHeartrate.permissionsCheck()) {
                healthConnectHeartrate.writeSource()
            }
        }
    }
}

//this class is used to init
class HeartrateViewModelFactory(private val healthConnectHeartrate: HealthConnectHeartrate) :
    ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(HeartrateViewModel::class.java))
        {
            @Suppress("UNCHECKED_CAST")
            return HeartrateViewModel(healthConnectHeartrate) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
