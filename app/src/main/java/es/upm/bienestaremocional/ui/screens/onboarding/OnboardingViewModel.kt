package es.upm.bienestaremocional.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.info.AppInfo
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appInfo: AppInfo
) : ViewModel() {
    fun onFinish() {
        viewModelScope.launch {
            //only quit first time info when the app exit onboarding screen
            appInfo.saveFirstTime(false)
        }
    }
}