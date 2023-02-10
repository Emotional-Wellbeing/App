package es.upm.bienestaremocional.app.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.ui.screen.NavGraphs
import es.upm.bienestaremocional.app.ui.screen.destinations.*
import es.upm.bienestaremocional.app.ui.state.SplashState
import es.upm.bienestaremocional.app.ui.viewmodel.*

/**
 * Manages the navigation in the app
 */

@Composable
fun AppNavigation()
{
    val snackbarHostState = remember { SnackbarHostState() }


    DestinationsNavHost(navGraph = NavGraphs.root, dependenciesContainerBuilder = {
        dependency(MyDataScreenDestination)
        {
            viewModel<MyDataViewModel>(factory = MyDataViewModelFactory(snackbarHostState))
        }
        dependency(OnboardingScreenDestination)
        {
            viewModel<OnboardingViewModel>(factory = OnboardingViewModel.Factory)
        }
        dependency(QuestionnaireRoundScreenDestination)
        {
            viewModel<QuestionnaireRoundViewModel>(factory = QuestionnaireRoundViewModel.Factory)
        }
        dependency(SettingsScreenDestination)
        {
            viewModel<SettingsViewModel>(factory = SettingsViewModel.Factory)
        }
        dependency(SplashScreenDestination)
        {
            remember {
                mutableStateOf(if (!MainApplication.alarmScheduler.canScheduleExactly()) //&& MainApplication.appSettings.getFirstTimeValue())
                    SplashState.LaunchDialog
                else
                    SplashState.SkipDialog) }
        }
    })
}