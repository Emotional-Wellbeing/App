package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced
import es.upm.bienestaremocional.app.ui.component.ShowQuestionnaireRound
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.QuestionnaireRoundScreenDestination
import es.upm.bienestaremocional.app.ui.state.DebugState
import es.upm.bienestaremocional.app.ui.viewmodel.DebugViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Debug screen with certain buttons to debug application. Only available on dev versions
 */
@Destination
@Composable
fun DebugScreen(navigator: DestinationsNavigator, viewModel: DebugViewModel = hiltViewModel())
{
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember {SnackbarHostState()}

    val state by viewModel.state.collectAsState()
    val questionnaireRoundsIncompleted by viewModel.questionnaireRoundsIncompleted.observeAsState(emptyList())
    val questionnaireRounds by viewModel.questionnaireRounds.observeAsState(emptyList())

    val onDeleteDatabaseMessage = stringResource(R.string.database_deleted)

    DebugScreen(navigator = navigator,
        state = state,
        snackbarHostState = snackbarHostState,
        questionnaireRoundsIncompleted = questionnaireRoundsIncompleted,
        questionnaireRounds = questionnaireRounds,
        onNotification = { viewModel.onNotification() },
        onQueryAllQuestionnaireRounds = {viewModel.onQueryAllQuestionnaireRounds()},
        onQueryUncompletedQuestionnaireRounds = {viewModel.onQueryUncompletedQuestionnaireRounds()},
        onDeleteDatabase = {
            coroutineScope.launch {
                viewModel.onDeleteDatabase()
                showSnackbar(snackbarHostState, onDeleteDatabaseMessage)
            }
        }
    )
}

@Composable
private fun DebugScreen(navigator: DestinationsNavigator,
                        state: DebugState,
                        snackbarHostState : SnackbarHostState,
                        questionnaireRoundsIncompleted: List<QuestionnaireRoundFull>,
                        questionnaireRounds: List<QuestionnaireRoundFull>,
                        onNotification: () -> Unit,
                        onQueryAllQuestionnaireRounds : () -> Unit,
                        onQueryUncompletedQuestionnaireRounds : () -> Unit,
                        onDeleteDatabase : () -> Unit
)
{

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.DebugScreen,
        label = BottomBarDestination.DebugScreen.label,
        snackbarHostState = snackbarHostState)
    {
        when(state)
        {
            DebugState.ShowOptions -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.notification_start_questionnaire),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onNotification,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.query_all_questionnaire_rounds),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onQueryAllQuestionnaireRounds,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.query_uncompleted_questionnaire_rounds),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onQueryUncompletedQuestionnaireRounds,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.delete_database),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onDeleteDatabase,
                    )
                }
            }
            DebugState.QueryAllQuestionnaireRounds -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp))
                {
                    if(questionnaireRounds.isEmpty())
                    {
                        item {
                            Text(stringResource(R.string.empty_list))
                        }
                    }
                    else
                    {
                        items(questionnaireRounds)
                        {
                            BasicCard {
                                ShowQuestionnaireRound(it)
                            }
                        }
                    }
                }
            }
            DebugState.QueryUncompletedQuestionnaireRounds -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp))
                {
                    if(questionnaireRoundsIncompleted.isEmpty())
                    {
                        item {
                            Text(stringResource(R.string.empty_list))
                        }
                    }
                    else
                    {
                        items(questionnaireRoundsIncompleted) { item ->
                            BasicCard {
                                ShowQuestionnaireRound(item)
                                TextButton(onClick = {
                                    val qrr = QuestionnaireRoundReduced(
                                        item.questionnaireRound.id,
                                        if(item.pss.completed) null else item.pss.id,
                                        item.phq?.let { if (it.completed) null else it.id },
                                        item.ucla?.let { if (it.completed) null else it.id })
                                    navigator.navigate(QuestionnaireRoundScreenDestination(questionnaireRoundReduced = qrr))
                                })
                                {
                                    Text(stringResource(R.string.continue_label),
                                        color = MaterialTheme.colorScheme.tertiary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
private suspend fun showSnackbar(snackbarHostState : SnackbarHostState, message : String) =
    snackbarHostState.showSnackbar(message = message)

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun DebugScreenPreview()
{
    BienestarEmocionalTheme{
        DebugScreen(navigator = EmptyDestinationsNavigator,
            state = DebugState.ShowOptions,
            snackbarHostState =  remember { SnackbarHostState() },
            questionnaireRounds = emptyList(),
            questionnaireRoundsIncompleted = emptyList(),
            onNotification = {},
            onQueryAllQuestionnaireRounds = {},
            onQueryUncompletedQuestionnaireRounds = {},
            onDeleteDatabase = {})
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun DebugScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        DebugScreen(navigator = EmptyDestinationsNavigator,
            state = DebugState.ShowOptions,
            snackbarHostState =  remember { SnackbarHostState() },
            questionnaireRounds = emptyList(),
            questionnaireRoundsIncompleted = emptyList(),
            onNotification = {},
            onQueryAllQuestionnaireRounds = {},
            onQueryUncompletedQuestionnaireRounds = {},
            onDeleteDatabase = {})
    }
}