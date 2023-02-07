package es.upm.bienestaremocional.app.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.ui.screen.NavGraphs
import es.upm.bienestaremocional.app.ui.screen.destinations.MyDataScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.OnboardingScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.QuestionnaireScreenDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.SettingsScreenDestination
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
        dependency(QuestionnaireScreenDestination)
        {
            //viewModel<QuestionnaireViewModel>(factory = QuestionnaireViewModelFactory(appSettings.getQuestionnairesSelectedValue()))
            viewModel<QuestionnaireViewModel>(factory = QuestionnaireViewModelFactory(Questionnaire.PSS))
        }
        dependency(SettingsScreenDestination)
        {
            viewModel<SettingsViewModel>(factory = SettingsViewModel.Factory)
        }
    })
}