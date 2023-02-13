package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import kotlinx.coroutines.launch

class OnboardingViewModel(private val appSettings: AppSettingsInterface) : ViewModel()
{
    companion object
    {
        val Factory : ViewModelProvider.Factory = viewModelFactory{
            initializer {
                OnboardingViewModel(appSettings = MainApplication.appSettings)
            }
        }
    }

    fun onFinish()
    {
        viewModelScope.launch {
            //only quit first time info when the app exit onboarding screen
            appSettings.saveFirstTime(false)
        }
    }
}