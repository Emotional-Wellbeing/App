package es.upm.bienestaremocional.app.ui.screens.debug

import android.widget.Toast
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.work.WorkInfo
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundFull
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced
import es.upm.bienestaremocional.app.ui.component.ShowQuestionnaireRound
import es.upm.bienestaremocional.app.ui.screens.destinations.QuestionnaireRoundScreenDestination
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard
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

    val state by viewModel.state.collectAsStateWithLifecycle()
    val questionnaireRoundsUncompleted by viewModel.questionnaireRoundsUncompleted.observeAsState(emptyList())
    val questionnaireRounds by viewModel.questionnaireRounds.observeAsState(emptyList())
    val workInfo by viewModel.workInfo.observeAsState(emptyList())

    val onPrepoulatedDatabaseMessage = stringResource(R.string.database_prepopulated)
    val onDeleteDatabaseMessage = stringResource(R.string.database_deleted)
    val onUploadTimestampsReset = stringResource(R.string.upload_timestamps_reset)

    val context = LocalContext.current

    DebugScreen(navigator = navigator,
        state = state,
        snackbarHostState = snackbarHostState,
        questionnaireRoundsUncompleted = questionnaireRoundsUncompleted,
        questionnaireRounds = questionnaireRounds,
        workInfo = workInfo,
        onNotification = viewModel::onNotification,
        onQueryAllQuestionnaireRounds = viewModel::onQueryAllQuestionnaireRounds,
        onQueryUncompletedQuestionnaireRounds = viewModel::onQueryUncompletedQuestionnaireRounds,
        onPrepoulateDatabase = {
            coroutineScope.launch {
                viewModel.onPrepoulateDatabase()
                showSnackbar(snackbarHostState, onPrepoulatedDatabaseMessage)
            }
        },
        onDeleteDatabase = {
            coroutineScope.launch {
                viewModel.onDeleteDatabase()
                showSnackbar(snackbarHostState, onDeleteDatabaseMessage)
            }
        },
        onNotificationWorker = { viewModel.onNotificationWorker(context) },
        onUploadWorker = { viewModel.onUploadWorker(context) },
        onGetScore = {
            coroutineScope.launch {
                val score = viewModel.onGetScore()
                score?.let {
                    val resultRequest = context.getString(R.string.result_of_request)
                    Toast.makeText(context,"$resultRequest: $it", Toast.LENGTH_LONG).show()
                } ?: Toast.makeText(context,context.getString(R.string.request_failed), Toast.LENGTH_LONG).show()
            }
        },
        onPostUserData = {
            coroutineScope.launch {
                val success = viewModel.onPostUserData()
                if (success)
                    Toast.makeText(context,context.getString(R.string.data_sent_successfully), Toast.LENGTH_LONG).show()
                else
                    Toast.makeText(context,context.getString(R.string.request_failed), Toast.LENGTH_LONG).show()
            }
        },
        onResetUploadTimestamps = {
            coroutineScope.launch {
                viewModel.onResetUploadTimestamps()
                showSnackbar(snackbarHostState, onUploadTimestampsReset)
            }
        },
        onQueryWorkerStatus = viewModel::onQueryWorkerStatus,
        onGetUID = {
            coroutineScope.launch {
                val uid = viewModel.onGetUID()
                Toast.makeText(context,uid, Toast.LENGTH_LONG).show()
            }
        }
    )
}

@Composable
private fun DebugScreen(navigator: DestinationsNavigator,
                        state: DebugState,
                        snackbarHostState : SnackbarHostState,
                        questionnaireRoundsUncompleted: List<QuestionnaireRoundFull>,
                        questionnaireRounds: List<QuestionnaireRoundFull>,
                        workInfo : List<WorkInfo>,
                        onNotification: () -> Unit,
                        onQueryAllQuestionnaireRounds : () -> Unit,
                        onQueryUncompletedQuestionnaireRounds : () -> Unit,
                        onPrepoulateDatabase : () -> Unit,
                        onDeleteDatabase : () -> Unit,
                        onNotificationWorker : () -> Unit,
                        onUploadWorker : () -> Unit,
                        onGetScore : () -> Unit,
                        onPostUserData: () -> Unit,
                        onResetUploadTimestamps : () -> Unit,
                        onQueryWorkerStatus: () -> Unit,
                        onGetUID: () -> Unit,
)
{

    AppBasicScreen(navigator = navigator,
        entrySelected = null,
        label = R.string.debug_screen_label,
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
                        title = { Text(text = stringResource(R.string.prepopulate_database),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onPrepoulateDatabase,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.delete_database),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onDeleteDatabase,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.test_notification_worker),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onNotificationWorker,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.test_upload_worker),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onUploadWorker,
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.test_get_score),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onGetScore
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.test_post_user_data),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onPostUserData
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.reset_upload_timestamps),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onResetUploadTimestamps
                    )

                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.query_worker_status),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onQueryWorkerStatus
                    )
                    SettingsMenuLink(
                        title = { Text(text = stringResource(R.string.query_uid),
                            color = MaterialTheme.colorScheme.secondary) },
                        onClick = onGetUID
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
            DebugState.QueryWorkManager ->
            {
                val tagsLabel = stringResource(R.string.tags)
                val stateLabel = stringResource(R.string.state)

                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp))
                {
                    items(workInfo) { item ->
                        BasicCard {
                            Text("$tagsLabel: ${item.tags}")
                            Text("$stateLabel: ${item.state}")
                        }
                    }
                }
            }
        }
    }
}
private suspend fun showSnackbar(snackbarHostState : SnackbarHostState, message : String) =
    snackbarHostState.showSnackbar(message = message)

/*
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
            questionnaireRoundsUncompleted = emptyList(),
            onNotification = {},
            onQueryAllQuestionnaireRounds = {},
            onQueryUncompletedQuestionnaireRounds = {},
            onPrepoulateDatabase = {},
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
            questionnaireRoundsUncompleted = emptyList(),
            onNotification = {},
            onQueryAllQuestionnaireRounds = {},
            onQueryUncompletedQuestionnaireRounds = {},
            onPrepoulateDatabase = {},
            onDeleteDatabase = {})
    }
}*/