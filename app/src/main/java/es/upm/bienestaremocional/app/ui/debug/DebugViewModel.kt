package es.upm.bienestaremocional.app.ui.debug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.ui.heartrate.HeartRateViewModel
import es.upm.bienestaremocional.app.ui.sleep.SleepSessionViewModel

class DebugViewModel(val sleepSessionViewModel: SleepSessionViewModel,
                     val heartRateViewModel: HeartRateViewModel) : ViewModel()
{
    companion object
    {
        /**
         * Factory class to instance [HeartRateViewModel]
         */
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                DebugViewModel(
                    sleepSessionViewModel = SleepSessionViewModel(
                        Sleep(
                            healthConnectClient = MainApplication.healthConnectClient,
                            healthConnectManager = MainApplication.healthConnectManager
                        )),
                    heartRateViewModel = HeartRateViewModel(
                        HeartRate(
                            healthConnectClient = MainApplication.healthConnectClient,
                            healthConnectManager = MainApplication.healthConnectManager
                        )
                    ),
                )
            }
        }
    }
}