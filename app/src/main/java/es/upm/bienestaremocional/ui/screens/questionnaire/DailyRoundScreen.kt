package es.upm.bienestaremocional.ui.screens.questionnaire

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import com.ramcosta.composedestinations.annotation.DeepLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.FULL_ROUTE_PLACEHOLDER
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.ui.GetOnceResult
import es.upm.bienestaremocional.destinations.HomeScreenDestination

/**
 * Plots questionnaires
 */
@Destination(
    deepLinks = [
        DeepLink(
            uriPattern = "be://dailyRound/$FULL_ROUTE_PLACEHOLDER"
        )
    ]
)
@Composable
fun DailyRoundScreen(
    navigator: DestinationsNavigator,
    navBackStackEntry: NavBackStackEntry,
    viewModel: DailyRoundViewModel = hiltViewModel(),
    dailyRound: DailyRound
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val logTag = viewModel.logTag

    navBackStackEntry.GetOnceResult<Boolean>("finished") {
        if (it)
            viewModel.onResumeRound()
    }

    when (state) {
        QuestionnaireRoundState.Init -> {
            viewModel.onInit()
        }

        QuestionnaireRoundState.PostShow -> {
            //dummy state to avoid multiple navigation for same questionnaire
        }

        QuestionnaireRoundState.Show -> {
            Log.d(logTag, "Showing questionnaire")
            viewModel.onShow(navigator = navigator)
        }

        QuestionnaireRoundState.Finishing -> {//TODO add insert database
            Log.d(logTag, "Finishing")
            viewModel.onFinishing()
        }

        QuestionnaireRoundState.Finished -> {
            navigator.navigate(HomeScreenDestination)
        }
    }
}