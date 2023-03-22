package es.upm.bienestaremocional.app.ui.screens.settings

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.health.connect.client.HealthConnectClient.Companion.ACTION_HEALTH_CONNECT_SETTINGS
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.base.rememberIntSetSettingState
import com.alorma.compose.settings.storage.base.rememberIntSettingState
import com.alorma.compose.settings.ui.SettingsList
import com.alorma.compose.settings.ui.SettingsListMultiSelect
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.MainApplication
import es.upm.bienestaremocional.app.data.alarm.AlarmsFrequency
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.screens.destinations.*
import es.upm.bienestaremocional.app.utils.android12OrAbove
import es.upm.bienestaremocional.app.utils.getActivity
import es.upm.bienestaremocional.app.utils.openForeignActivity
import es.upm.bienestaremocional.app.utils.restartApp
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.launch

/**
 * Public function to read SettingsScreen using [SettingsViewModel]
 */
@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator,
                   viewModel: SettingsViewModel = hiltViewModel()
)
{
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val restartToApplyChanges = stringResource(id = R.string.restart_apply_changes)
    val restartApplyAllChanges = stringResource(id = R.string.restart_apply_all_changes)
    val actionLabel = stringResource(id = R.string.restart)

    val alarmFrequency = rememberIntSettingState(viewModel.loadAlarmFrequency())
    val questionnaire = rememberIntSetSettingState(viewModel.loadQuestionnairesSelected())
    val language = rememberIntSettingState(viewModel.loadLanguage())
    val themeMode = rememberIntSettingState(viewModel.loadDarkMode())
    val dynamicColor = rememberBooleanSettingState(viewModel.loadDynamicColors())

    val android12OrAbove = android12OrAbove()
    val languagesAvailable = viewModel.getLanguagesAvailable()

    val onAlarmFrequencyChange : (Int) -> Unit  = { viewModel.changeAlarmFrequency(it)}
    val onQuestionnairesChange : (Set<Int>) -> Unit = { viewModel.changeQuestionnairesSelected(it) }
    val onLanguageChange : (Int) -> Unit = { lang ->
        viewModel.changeLanguage(context, lang)
        coroutineScope.launch {
            showRestartInfo(snackbarHostState = snackbarHostState,
                message = restartApplyAllChanges,
                actionLabel = actionLabel,
                context = context)
        }
    }
    val onThemeChange : (Int) -> Unit = { theme ->
        viewModel.changeDarkMode(theme)
        coroutineScope.launch {
            showRestartInfo(snackbarHostState = snackbarHostState,
                message = restartToApplyChanges,
                actionLabel = actionLabel,
                context = context)
        }
    }
    val onDynamicChange : (Boolean) -> Unit = { dynamic ->
        viewModel.changeDynamicColors(dynamic)
        coroutineScope.launch {
            showRestartInfo(snackbarHostState = snackbarHostState,
                message = restartToApplyChanges,
                actionLabel = actionLabel,
                context = context)
        }
    }
    val onSettingsApplication = { viewModel.openSettingsApplication(context) }
    val onSettingsNotifications = { viewModel.openSettingsNotifications(context) }
    val onSettingsExactNotifications = { viewModel.openSettingsExactNotifications(context) }

    SettingsScreen(
        navigator = navigator,
        snackbarHostState = snackbarHostState,
        alarmFrequency = alarmFrequency,
        questionnaires = questionnaire,
        language = language,
        themeMode = themeMode,
        dynamicColor = dynamicColor,
        android12OrAbove = android12OrAbove,
        languagesAvailable = languagesAvailable,
        onAlarmFrequencyChange = onAlarmFrequencyChange,
        onQuestionnairesChange = onQuestionnairesChange,
        onLanguageChange = onLanguageChange,
        onThemeChange = onThemeChange,
        onDynamicChange = onDynamicChange,
        onSettingsApplication = onSettingsApplication,
        onSettingsNotifications = onSettingsNotifications,
        onSettingsExactNotifications = onSettingsExactNotifications
    )
}

/**
 * Renders settings menu
 * @param navigator: needed for render menu
 * @param alarmFrequency: var that holds questionnaire frequency
 * @param questionnaires: var that stores additional questionnaires selection
 * @param language: var that stores the language of the app
 * @param themeMode: var that stores theme setting value
 * @param dynamicColor: var that stores dynamic setting value
 * @param android12OrAbove: boolean to print Android 12+ options
 * @param languagesAvailable: list with the languages supported by the app
 * @param onAlarmFrequencyChange: callback to react alarm frequency setting changes
 * @param onQuestionnairesChange: callback to react questionnaires changes
 * @param onLanguageChange: callback to react language setting changes
 * @param onThemeChange: callback to react theme setting changes
 * @param onDynamicChange: callback to react dynamic setting changes
 * @param onSettingsApplication: executed when user press application's setting
 * @param onSettingsNotifications: executed when user press notification's setting
 * @param onSettingsExactNotifications: executed when user press exact notification's setting (Android 12+)
 */
@Composable
private fun SettingsScreen(navigator: DestinationsNavigator,
                           snackbarHostState: SnackbarHostState,
                           alarmFrequency: SettingValueState<Int>,
                           questionnaires: SettingValueState<Set<Int>>,
                           language: SettingValueState<Int>,
                           themeMode: SettingValueState<Int>,
                           dynamicColor : SettingValueState<Boolean>,
                           android12OrAbove : Boolean,
                           languagesAvailable : List<String>,
                           onAlarmFrequencyChange : (Int) -> Unit,
                           onQuestionnairesChange :  (Set<Int>) -> Unit,
                           onLanguageChange : (Int) -> Unit,
                           onThemeChange : (Int) -> Unit,
                           onDynamicChange : (Boolean) -> Unit,
                           onSettingsApplication: () -> Unit,
                           onSettingsNotifications: () -> Unit,
                           onSettingsExactNotifications: () -> Unit
)

{
    val context = LocalContext.current
    val windowSize = MainApplication.windowSize!!

    val questionnaireOptions : List<String> = Questionnaire.getOptionalLabels()

    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = BottomBarDestination.SettingsScreen.label,
        snackbarHostState = snackbarHostState
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            GroupText(textRes = R.string.privacy_group)

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.storage),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.my_data_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.my_data_description)) },
                onClick = { navigator.navigate(MyDataScreenDestination(windowSize)) },
            )


            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.security),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.privacy_policy_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.privacy_policy_screen_description)) },
                onClick = { navigator.navigate(PrivacyPolicyScreenDestination) },
            )

            SettingsMenuLink(
                    icon = { Icon(painter = painterResource(R.drawable.health_connect_logo),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier(),
                        tint = Color.Unspecified) },
            title = { Text(text = stringResource(id = R.string.health_connect_settings_label),
                color = MaterialTheme.colorScheme.secondary) },
            subtitle = { Text(stringResource(id = R.string.health_connect_settings_description)) },
            onClick = { openForeignActivity(context = context, action = ACTION_HEALTH_CONNECT_SETTINGS ) },
            )

            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.ui_group)

            SettingsList(
                icon = { Icon(painter = painterResource(R.drawable.language),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.language),
                    color = MaterialTheme.colorScheme.secondary) },
                state = language,
                items = languagesAvailable,
                onItemSelected = {index, _ -> onLanguageChange(index)}
            )

            if (android12OrAbove)
            {
                SettingsSwitch(
                    icon = { Icon(painter = painterResource(R.drawable.palette),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()) },
                    title = { Text(text = stringResource(R.string.dynamic_colors_label),
                        color = MaterialTheme.colorScheme.secondary) },
                    subtitle = { Text(text = stringResource(R.string.dynamic_colors_description)) },
                    state = dynamicColor,
                    onCheckedChange = { onDynamicChange(it) }
                )
            }

            SettingsList(
                icon = { Icon(painter = painterResource(R.drawable.dark_mode),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.dark_mode),
                    color = MaterialTheme.colorScheme.secondary) },
                state = themeMode,
                items = ThemeMode.getLabels(),
                onItemSelected = {index, _ -> onThemeChange(index)}
            )


            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.notifications)

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.settings_applications),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.application_settings),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(R.string.application_settings_label)) },
                onClick = onSettingsApplication,
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.notifications),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.permission_for_notifications),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(R.string.permission_for_notifications_body)) },
                onClick = onSettingsNotifications,
            )

            if (android12OrAbove)
            {
                SettingsMenuLink(
                    icon = { Icon(painter = painterResource(R.drawable.notification_important),
                            contentDescription = null,
                            modifier = Modifier.defaultIconModifier())},
                    title = { Text(stringResource(R.string.permission_for_exact_notifications),
                        color = MaterialTheme.colorScheme.secondary) },
                    subtitle = { Text(stringResource(R.string.permission_for_exact_notifications_body)) },
                    onClick = onSettingsExactNotifications,
                )
            }


            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.feedback_group)

            SettingsList(
                icon = { Icon(painter = painterResource(R.drawable.event_repeat),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.feedback_frequency),
                    color = MaterialTheme.colorScheme.secondary) },
                state = alarmFrequency,
                items = AlarmsFrequency.getLabels(),
                onItemSelected = { index, _ -> onAlarmFrequencyChange(index) }
            )

            SettingsListMultiSelect(
                icon = { Icon(painter = painterResource(R.drawable.question_answer),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.additional_questionnaires),
                    color = MaterialTheme.colorScheme.secondary) },
                state = questionnaires,
                items = questionnaireOptions,
                confirmButton = stringResource(R.string.accept),
                onItemsSelected = { items ->
                    val indexes = items
                        .map { item -> questionnaireOptions.indexOf(item) }
                        .filter { it >= 0 }
                    onQuestionnairesChange(indexes.toSet())
                }
            )

            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.misc_group)

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.help_outline),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.about_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.about_screen_description)) },
                onClick = { navigator.navigate(AboutScreenDestination) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.start),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.onboarding_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.onboarding_screen_description)) },
                onClick = { navigator.navigate(OnboardingScreenDestination(windowSize)) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.people_alt),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.credits_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.credits_screen_description)) },
                onClick = { navigator.navigate(CreditsScreenDestination(windowSize)) },
            )

            Divider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.developer_options)

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.developer_mode),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.debug_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                onClick = { navigator.navigate(DebugScreenDestination) },
            )

        }
    }
}

/**
 * Text with custom style to split group of settings
 */
@Composable
private fun GroupText(@StringRes textRes : Int) =
    Text(text = stringResource(textRes),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary)

/**
 * Default icon padding and size for this screen
 */
private fun Modifier.defaultIconModifier() = this.then(padding(all = 2.dp).size(size = 28.dp))

/**
 * Show snackbar to restart app in order to show properly user's changes
 */

private suspend fun showRestartInfo(snackbarHostState: SnackbarHostState,
                                    message : String,
                                    actionLabel : String,
                                    context: Context)
{
    val result = snackbarHostState.showSnackbar(message = message, actionLabel = actionLabel,
        withDismissAction = true, duration = SnackbarDuration.Long)
    if (result === SnackbarResult.ActionPerformed)
        restartApp(activity = context.getActivity())
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreview()
{
    BienestarEmocionalTheme()
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState =  remember { SnackbarHostState() },
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = false,
            languagesAvailable = listOf(),
            onAlarmFrequencyChange = {},
            onQuestionnairesChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
            onSettingsExactNotifications = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState =  remember { SnackbarHostState() },
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = false,
            languagesAvailable = listOf(),
            onAlarmFrequencyChange = {},
            onQuestionnairesChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
            onSettingsExactNotifications = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview()
{
    BienestarEmocionalTheme()
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState =  remember { SnackbarHostState() },
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = true,
            languagesAvailable = listOf(),
            onAlarmFrequencyChange = {},
            onQuestionnairesChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
            onSettingsExactNotifications = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState =  remember { SnackbarHostState() },
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = true,
            languagesAvailable = listOf(),
            onAlarmFrequencyChange = {},
            onQuestionnairesChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
            onSettingsExactNotifications = {}
        )
    }
}