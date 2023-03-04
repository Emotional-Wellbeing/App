package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.settings.AppSettings
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appSettings: AppSettings
) : ViewModel()
{
    fun onFinish()
    {
        viewModelScope.launch {
            //only quit first time info when the app exit onboarding screen
            appSettings.saveFirstTime(false)
        }
    }
}