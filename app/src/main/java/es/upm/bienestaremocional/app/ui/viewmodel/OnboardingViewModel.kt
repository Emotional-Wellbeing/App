package es.upm.bienestaremocional.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val appSettings: AppSettingsInterface
) : ViewModel()
{
    fun onFinish()
    {
        viewModelScope.launch {
            //only quit first time info when the app exit onboarding screen
            appSettings.saveFirstTime(false)
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    fun onNextPage(pagerState: PagerState)
    {
        viewModelScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    fun onPreviousPage(pagerState: PagerState)
    {
        viewModelScope.launch {
            pagerState.animateScrollToPage(
                pagerState.currentPage - 1,
                pagerState.currentPageOffset
            )
        }
    }
}