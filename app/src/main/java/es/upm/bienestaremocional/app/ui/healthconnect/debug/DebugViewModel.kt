package es.upm.bienestaremocional.app.ui.healthconnect.debug

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.healthconnect.sources.BasalMetabolicRate
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.app.ui.healthconnect.debug.bmr.BasalMetabolicRateViewModel
import es.upm.bienestaremocional.app.ui.healthconnect.heartrate.HeartRateViewModel
import es.upm.bienestaremocional.app.ui.healthconnect.sleep.SleepSessionViewModel
import es.upm.bienestaremocional.app.ui.healthconnect.steps.StepsViewModel

class DebugViewModel(val sleepSessionViewModel: SleepSessionViewModel,
                     val heartRateViewModel: HeartRateViewModel,
                     val stepsViewModel: StepsViewModel,
                     val basalMetabolicRateViewModel: BasalMetabolicRateViewModel) : ViewModel()
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
                        )
                    ),
                    heartRateViewModel = HeartRateViewModel(
                        HeartRate(
                            healthConnectClient = MainApplication.healthConnectClient,
                            healthConnectManager = MainApplication.healthConnectManager
                        )
                    ),
                    stepsViewModel = StepsViewModel(
                        Steps(
                            healthConnectClient = MainApplication.healthConnectClient,
                            healthConnectManager = MainApplication.healthConnectManager
                        )
                    ),
                    basalMetabolicRateViewModel = BasalMetabolicRateViewModel(
                        BasalMetabolicRate(
                            healthConnectClient = MainApplication.healthConnectClient,
                            healthConnectManager = MainApplication.healthConnectManager
                        )
                    )
                )
            }
        }
    }
}