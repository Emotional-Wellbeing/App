package es.upm.bienestaremocional.ui.screens.uncompleted

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.BackHandlerMinimizeApp
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.ShowUncompletedDailyRound
import es.upm.bienestaremocional.ui.component.ShowUncompletedOneOffRound
import es.upm.bienestaremocional.ui.screens.destinations.DailyRoundScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OneOffRoundScreenDestination

@Destination
@Composable
fun UncompletedQuestionnairesScreen(
    navigator: DestinationsNavigator,
    viewModel: UncompletedQuestionnairesViewModel = hiltViewModel()
) {
    BackHandlerMinimizeApp(LocalContext.current)

    val dailyRoundsUncompleted by viewModel.dailyRoundsUncompleted.observeAsState(emptyList())
    val oneOffRoundsUncompleted by viewModel.oneOffRoundsUncompleted.observeAsState(emptyList())

    UncompletedQuestionnairesScreen(
        navigator = navigator,
        dailyRoundsUncompleted = dailyRoundsUncompleted,
        oneOffRoundsUncompleted = oneOffRoundsUncompleted
    )
}

@Composable
fun UncompletedQuestionnairesScreen(
    navigator: DestinationsNavigator,
    dailyRoundsUncompleted: List<DailyRoundFull>,
    oneOffRoundsUncompleted: List<OneOffRoundFull>
) {
    AppBasicScreen(
        navigator = navigator,
        entrySelected = null,
        label = R.string.uncompleted_questionnaires_label
    )
    {
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            if (dailyRoundsUncompleted.isEmpty() && oneOffRoundsUncompleted.isEmpty()) {
                item {
                    Text(stringResource(R.string.empty_list))
                }
            }
            else {
                items(dailyRoundsUncompleted) { item ->
                    BasicCard {
                        ShowUncompletedDailyRound(item)
                        TextButton(onClick = {
                            navigator.navigate(
                                DailyRoundScreenDestination(
                                    dailyRound = item.dailyRound
                                )
                            )
                        })
                        {
                            Text(
                                stringResource(R.string.continue_label),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }

                items(oneOffRoundsUncompleted) { item ->
                    BasicCard {
                        ShowUncompletedOneOffRound(item)
                        TextButton(onClick = {
                            navigator.navigate(OneOffRoundScreenDestination(oneOffRound = item.oneOffRound))
                        })
                        {
                            Text(
                                stringResource(R.string.continue_label),
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }
                    }
                }
            }
        }
    }
}