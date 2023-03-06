package es.upm.bienestaremocional.app.ui.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.FULL_ROUTE_PLACEHOLDER
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced
import es.upm.bienestaremocional.app.ui.screen.destinations.HomeScreenDestination
import es.upm.bienestaremocional.app.ui.state.QuestionnaireRoundState
import es.upm.bienestaremocional.app.ui.viewmodel.QuestionnaireRoundViewModel
import es.upm.bienestaremocional.core.ui.GetOnceResult

/**
 * Plots questionnaires
 */
@Destination(
    deepLinks = [
        DeepLink(
            uriPattern = "be://questionnaire/$FULL_ROUTE_PLACEHOLDER"
        )
    ]
)
@Composable
fun QuestionnaireRoundScreen(navigator: DestinationsNavigator,
                             navBackStackEntry: NavBackStackEntry,
                             viewModel: QuestionnaireRoundViewModel = hiltViewModel(),
                             questionnaireRoundReduced: QuestionnaireRoundReduced
)
{
    val state by viewModel.state.collectAsState()
    val logTag = viewModel.logTag

    navBackStackEntry.GetOnceResult<Boolean>("finished"){
        if (it)
            viewModel.onResumeRound()
    }

    when(state)
    {
        QuestionnaireRoundState.Init -> { viewModel.onInit() }
        QuestionnaireRoundState.PostShow -> {
            //dummy state to avoid multiple navigation for same questionnaire
        }

        QuestionnaireRoundState.Show -> {
            Log.d(logTag,"Showing questionnaire")
            viewModel.onShow(navigator = navigator)
        }
        QuestionnaireRoundState.Finishing -> {//TODO add insert database
            Log.d(logTag,"Finishing")
            viewModel.onFinishing()
        }
        QuestionnaireRoundState.Finished -> {
            navigator.navigate(HomeScreenDestination)
        }
    }
}