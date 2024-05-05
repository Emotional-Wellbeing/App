package es.upm.bienestaremocional.ui.screens.onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.ui.screens.destinations.OnboardingScreenDestination
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appInfo: AppInfo
) : ViewModel() {

    //Flag that indicates if onboarding is opened as standalone window
    private val standalone = OnboardingScreenDestination.argsFrom(savedStateHandle).standalone
    fun onFinish() {
        if (!standalone) {
            viewModelScope.launch {
                //only quit first time info when the app exit onboarding screen
                appInfo.saveFirstTime(false)
            }
        }

    }
}