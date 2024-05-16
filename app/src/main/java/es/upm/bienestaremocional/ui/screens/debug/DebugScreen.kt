package es.upm.bienestaremocional.ui.screens.debug

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import es.upm.bienestaremocional.data.database.entity.round.DailyRoundFull
import es.upm.bienestaremocional.data.database.entity.round.OneOffRoundFull
import es.upm.bienestaremocional.data.remote.community.CommunityResponse
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.BasicCard
import es.upm.bienestaremocional.ui.component.CommunityRow
import es.upm.bienestaremocional.ui.component.ShowDailyRound
import es.upm.bienestaremocional.ui.component.ShowOneOffRound
import es.upm.bienestaremocional.ui.component.ShowUncompletedDailyRound
import es.upm.bienestaremocional.ui.component.ShowUncompletedOneOffRound
import es.upm.bienestaremocional.ui.screens.destinations.DailyRoundScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OneOffRoundScreenDestination
import kotlinx.coroutines.launch

/**
 * Debug screen with certain buttons to debug application. Only available on dev versions
 */
@Destination
@Composable
fun DebugScreen(navigator: DestinationsNavigator, viewModel: DebugViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val state by viewModel.state.collectAsStateWithLifecycle()
    val dailyRoundsUncompleted by viewModel.dailyRoundsUncompleted.observeAsState(emptyList())
    val dailyRounds by viewModel.dailyRounds.observeAsState(emptyList())
    val oneOffRoundsUncompleted by viewModel.oneOffRoundsUncompleted.observeAsState(emptyList())
    val oneOffRounds by viewModel.oneOffRounds.observeAsState(emptyList())
    val workInfo by viewModel.workInfo.observeAsState(emptyList())
    val communityData by viewModel.communityData.observeAsState()

    val onPrepoulatedDatabaseMessage = stringResource(R.string.database_prepopulated)
    val onDeleteDatabaseMessage = stringResource(R.string.database_deleted)
    val onUploadTimestampsReset = stringResource(R.string.upload_timestamps_reset)

    val context = LocalContext.current

    DebugScreen(
        navigator = navigator,
        state = state,
        snackbarHostState = snackbarHostState,
        dailyRoundsUncompleted = dailyRoundsUncompleted,
        dailyRounds = dailyRounds,
        oneOffRoundsUncompleted = oneOffRoundsUncompleted,
        oneOffRounds = oneOffRounds,
        workInfo = workInfo,
        communityData = communityData,
        onDailyMorningNotification = viewModel::onDailyMorningNotification,
        onDailyNightNotification = viewModel::onDailyNightNotification,
        onOneOffNotification = viewModel::onOneOffNotification,
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
        onDailyMorningNotificationWorker = { viewModel.onDailyMorningNotificationWorker(context) },
        onDailyNightNotificationWorker = { viewModel.onDailyNightNotificationWorker(context) },
        onOneOffNotificationWorker = { viewModel.onOneOffNotificationWorker(context) },
        onUploadWorker = { viewModel.onUploadWorker(context) },
        onCommunity = viewModel::onCommunity,
        onPostUserData = {
            coroutineScope.launch {
                val success = viewModel.onPostUserData()
                if (success)
                    Toast.makeText(
                        context,
                        context.getString(R.string.data_sent_successfully),
                        Toast.LENGTH_LONG
                    ).show()
                else
                    Toast.makeText(
                        context,
                        context.getString(R.string.request_failed),
                        Toast.LENGTH_LONG
                    ).show()
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
                Toast.makeText(context, uid, Toast.LENGTH_LONG).show()
            }
        }
    )
}

@Composable
private fun DebugScreen(
    navigator: DestinationsNavigator,
    state: DebugState,
    snackbarHostState: SnackbarHostState,
    dailyRoundsUncompleted: List<DailyRoundFull>,
    dailyRounds: List<DailyRoundFull>,
    oneOffRoundsUncompleted: List<OneOffRoundFull>,
    oneOffRounds: List<OneOffRoundFull>,
    workInfo: List<WorkInfo>,
    communityData: CommunityResponse.Data?,
    onDailyMorningNotification: () -> Unit,
    onDailyNightNotification: () -> Unit,
    onOneOffNotification: () -> Unit,
    onQueryAllQuestionnaireRounds: () -> Unit,
    onQueryUncompletedQuestionnaireRounds: () -> Unit,
    onPrepoulateDatabase: () -> Unit,
    onDeleteDatabase: () -> Unit,
    onDailyMorningNotificationWorker: () -> Unit,
    onDailyNightNotificationWorker: () -> Unit,
    onOneOffNotificationWorker: () -> Unit,
    onUploadWorker: () -> Unit,
    onCommunity: () -> Unit,
    onPostUserData: () -> Unit,
    onResetUploadTimestamps: () -> Unit,
    onQueryWorkerStatus: () -> Unit,
    onGetUID: () -> Unit,
) {

    AppBasicScreen(
        navigator = navigator,
        entrySelected = null,
        label = R.string.debug_screen_label,
        snackbarHostState = snackbarHostState
    )
    {
        when (state) {
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
                        title = {
                            Text(
                                text = stringResource(R.string.daily_morning_notification_start_questionnaire),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onDailyMorningNotification,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.daily_night_start_questionnaire),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onDailyNightNotification,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.one_off_start_questionnaire),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onOneOffNotification,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.query_all_questionnaire_rounds),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onQueryAllQuestionnaireRounds,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.query_uncompleted_questionnaire_rounds),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onQueryUncompletedQuestionnaireRounds,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.prepopulate_database),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onPrepoulateDatabase,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.delete_database),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onDeleteDatabase,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.test_daily_morning_notification_worker),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onDailyMorningNotificationWorker,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.test_daily_night_notification_worker),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onDailyNightNotificationWorker,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.test_one_off_notification_worker),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onOneOffNotificationWorker,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.test_upload_worker),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onUploadWorker,
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.test_get_community),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onCommunity
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.test_post_user_data),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onPostUserData
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.reset_upload_timestamps),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onResetUploadTimestamps
                    )

                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.query_worker_status),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onQueryWorkerStatus
                    )
                    SettingsMenuLink(
                        title = {
                            Text(
                                text = stringResource(R.string.query_uid),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = onGetUID
                    )
                }
            }

            DebugState.QueryAllQuestionnaireRounds -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    if (oneOffRounds.isEmpty() && dailyRounds.isEmpty()) {
                        item {
                            Text(stringResource(R.string.empty_list))
                        }
                    }
                    else {
                        item {
                            Text(text = stringResource(id = R.string.daily_rounds))
                        }
                        items(dailyRounds)
                        {
                            BasicCard {
                                ShowDailyRound(it)
                            }
                        }

                        item {
                            Text(text = stringResource(id = R.string.one_off_rounds))
                        }
                        items(oneOffRounds)
                        {
                            BasicCard {
                                ShowOneOffRound(it)
                            }
                        }
                    }
                }
            }

            DebugState.QueryUncompletedQuestionnaireRounds -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    if (oneOffRoundsUncompleted.isEmpty() && dailyRounds.isEmpty()) {
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

            DebugState.QueryWorkManager -> {
                val tagsLabel = stringResource(R.string.tags)
                val stateLabel = stringResource(R.string.state)

                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    items(workInfo) { item ->
                        BasicCard {
                            Text("$tagsLabel: ${item.tags}")
                            Text("$stateLabel: ${item.state}")
                        }
                    }
                }
            }

            DebugState.GetCommunity -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    communityData?.let {
                        item {
                            CommunityRow(it.yesterday, stringResource(id = R.string.yesterday))
                        }

                        item {
                            CommunityRow(
                                it.lastSevenDays,
                                stringResource(id = R.string.last_seven_days)
                            )
                        }

                        item {
                            Text(text = stringResource(id = R.string.actual_week))
                        }


                        it.currentWeek.getOrNull(0)?.let { monday ->
                            item {
                                CommunityRow(monday, stringResource(id = R.string.monday))
                            }
                        }
                        it.currentWeek.getOrNull(1)?.let { tuesday ->
                            item {
                                CommunityRow(tuesday, stringResource(id = R.string.tuesday))
                            }
                        }
                        it.currentWeek.getOrNull(2)?.let { wednesday ->
                            item {
                                CommunityRow(wednesday, stringResource(id = R.string.wednesday))
                            }
                        }
                        it.currentWeek.getOrNull(3)?.let { thursday ->
                            item {
                                CommunityRow(thursday, stringResource(id = R.string.thursday))
                            }
                        }
                        it.currentWeek.getOrNull(4)?.let { friday ->
                            item {
                                CommunityRow(friday, stringResource(id = R.string.friday))
                            }
                        }
                        it.currentWeek.getOrNull(5)?.let { saturday ->
                            item {
                                CommunityRow(saturday, stringResource(id = R.string.saturday))
                            }
                        }
                        it.currentWeek.getOrNull(6)?.let { sunday ->
                            item {
                                CommunityRow(sunday, stringResource(id = R.string.sunday))
                            }
                        }
                    }
                }
            }
        }
    }
}

private suspend fun showSnackbar(snackbarHostState: SnackbarHostState, message: String) =
    snackbarHostState.showSnackbar(message = message)
