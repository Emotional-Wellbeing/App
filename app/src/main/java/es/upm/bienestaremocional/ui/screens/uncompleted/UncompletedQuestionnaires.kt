package es.upm.bienestaremocional.ui.screens.uncompleted

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.data.database.entity.QuestionnaireRoundReduced
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.BackHandlerMinimizeApp
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.ShowUncompletedQuestionnaireRound
import es.upm.bienestaremocional.ui.screens.destinations.QuestionnaireRoundScreenDestination

@Destination
@Composable
fun UncompletedQuestionnairesScreen(
    navigator: DestinationsNavigator,
    viewModel: UncompletedQuestionnairesViewModel = hiltViewModel()
)
{
    BackHandlerMinimizeApp(LocalContext.current)

    val questionnaireRoundsUncompleted by viewModel.questionnaireRoundsUncompleted.observeAsState(emptyList())

    UncompletedQuestionnairesScreen(
        navigator = navigator,
        questionnaireRoundsUncompleted = questionnaireRoundsUncompleted,
    )
}

@Composable
fun UncompletedQuestionnairesScreen(
    navigator: DestinationsNavigator,
    questionnaireRoundsUncompleted : List<QuestionnaireRoundFull>)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = null,
        label = R.string.uncompleted_questionnaires_label)
    {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp))
        {
            if(questionnaireRoundsUncompleted.isEmpty())
            {
                item {
                    Text(stringResource(R.string.empty_list))
                }
            }
            else
            {
                items(questionnaireRoundsUncompleted) { item ->
                    BasicCard {
                        ShowUncompletedQuestionnaireRound(item)
                        Row(horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth())
                        {
                            TextButton(onClick = {
                                val qrr = QuestionnaireRoundReduced(
                                    item.questionnaireRound.id,
                                    if(item.pss.completed) null else item.pss.id,
                                    item.phq?.let { if (it.completed) null else it.id },
                                    item.ucla?.let { if (it.completed) null else it.id })
                                navigator.navigate(QuestionnaireRoundScreenDestination(questionnaireRoundReduced = qrr))
                            })
                            {
                                Text(
                                    stringResource(R.string.continue_label),
                                    color = MaterialTheme.colorScheme.tertiary)
                            }
                        }

                    }
                }
            }
        }
    }

}