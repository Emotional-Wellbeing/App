package es.upm.bienestaremocional.app.ui.screen

import android.app.Activity
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import es.upm.bienestaremocional.app.ui.navigation.MenuEntry
import es.upm.bienestaremocional.app.ui.screen.destinations.*
import es.upm.bienestaremocional.app.ui.viewmodel.SettingsViewModel
import es.upm.bienestaremocional.app.utils.android12OrAbove
import es.upm.bienestaremocional.app.utils.openForeignActivity
import es.upm.bienestaremocional.app.utils.restartApp
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

@Composable
private fun GroupText(@StringRes textRes : Int)
{
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.Start)
    {
        Text(text = stringResource(textRes),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary)
    }

}

private fun Modifier.defaultIconModifier() = this.then(padding(all = 2.dp).size(size = 28.dp))


private suspend fun showRestartInfo(snackbarHostState: SnackbarHostState,
                                    message : String,
                                    actionLabel : String,
                                    context: Context)
{
    val result = snackbarHostState.showSnackbar(message = message, actionLabel = actionLabel,
        withDismissAction = true, duration = SnackbarDuration.Long)
    if (result === SnackbarResult.ActionPerformed)
        restartApp(activity = context as Activity)
}

private const val HEALTH_CONNECT_ACTION = "androidx.health.ACTION_HEALTH_CONNECT_SETTINGS"

/**
 * Renders settings menu
 * @param navigator: needed for render menu
 * @param alarmFrequency: var that holds questionnaire frequency
 * @param questionnaires: var that stores additional questionnaires selection
 * @param language: var that stores the language of the app
 * @param themeMode: var that stores theme setting value
 * @param dynamicColor: var that stores dynamic setting value
 * @param android12OrAbove: boolean to print Android 12+ options
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
private fun DrawSettingsScreen(navigator: DestinationsNavigator,
                               alarmFrequency: SettingValueState<Int>,
                               questionnaires: SettingValueState<Set<Int>>,
                               language: SettingValueState<Int>,
                               themeMode: SettingValueState<Int>,
                               dynamicColor : SettingValueState<Boolean>,
                               android12OrAbove : Boolean,
                               onAlarmFrequencyChange : suspend (SettingValueState<Int>) -> Unit,
                               onQuestionnairesChange : suspend (SettingValueState<Set<Int>>) -> Unit,
                               onLanguageChange : @Composable (SettingValueState<Int>) -> Unit,
                               onThemeChange : suspend (SettingValueState<Int>) -> Unit,
                               onDynamicChange : suspend (SettingValueState<Boolean>) -> Unit,
                               onSettingsApplication: () -> Unit,
                               onSettingsNotifications: () -> Unit,
                               onSettingsExactNotifications: () -> Unit
)

{
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val windowSize = MainApplication.windowSize!!

    //avoid undesired launch
    val defaultAlarmFrequency : Int = remember { alarmFrequency.value }
    val alarmFrequencyChanged : MutableState<Boolean> = remember { mutableStateOf(false) }
    val defaultQuestionnaires : Set<Int> = remember { questionnaires.value }
    val defaultThemeValue : Int = remember { themeMode.value }
    val defaultDynamicValue : Boolean = remember { dynamicColor.value }
    val defaultLanguage : Int = remember { language.value }

    val restartToApplyChanges = stringResource(id = R.string.restart_apply_changes)
    val restartApplyAllChanges = stringResource(id = R.string.restart_apply_all_changes)
    val actionLabel = stringResource(id = R.string.restart)

    if (alarmFrequency.value != defaultAlarmFrequency || alarmFrequencyChanged.value)
    {
        if (alarmFrequency.value != defaultAlarmFrequency)
            alarmFrequencyChanged.value = true
        LaunchedEffect(alarmFrequency.value)
        {
            onAlarmFrequencyChange(alarmFrequency)
        }
    }

    if (questionnaires.value != defaultQuestionnaires)
    {
        LaunchedEffect(questionnaires.value)
        {
            onQuestionnairesChange(questionnaires)
        }
    }

    if (themeMode.value != defaultThemeValue)
    {
        LaunchedEffect(themeMode.value)
        {
            onThemeChange(themeMode)
            showRestartInfo(snackbarHostState = snackbarHostState,
                message = restartToApplyChanges,
                actionLabel = actionLabel,
                context = context)
        }
    }

    if (dynamicColor.value != defaultDynamicValue)
    {
        LaunchedEffect(dynamicColor.value)
        {
            onDynamicChange(dynamicColor)
            showRestartInfo(snackbarHostState = snackbarHostState,
                message = restartToApplyChanges,
                actionLabel = actionLabel,
                context = context)
        }
    }

    if (language.value != defaultLanguage)
    {
        onLanguageChange(language)
        LaunchedEffect(language.value)
        {
            showRestartInfo(snackbarHostState = snackbarHostState,
                message = restartApplyAllChanges,
                actionLabel = actionLabel,
                context = context)
        }
    }


    AppBasicScreen(navigator = navigator,
        entrySelected = MenuEntry.SettingsScreen,
        label = MenuEntry.SettingsScreen.labelId,
        scope = scope,
        snackbarHostState = snackbarHostState
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
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
            onClick = { openForeignActivity(context = context, action = HEALTH_CONNECT_ACTION) },
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
                items = MainApplication.languageManager.getSupportedLocalesLabel()
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
                    state = dynamicColor
                )
            }

            SettingsList(
                icon = { Icon(painter = painterResource(R.drawable.dark_mode),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.dark_mode),
                    color = MaterialTheme.colorScheme.secondary) },
                state = themeMode,
                items = ThemeMode.getLabels()
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
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.notification_important),
                            contentDescription = null,
                            modifier = Modifier.defaultIconModifier()
                        )
                    },
                    title = {
                        Text(
                            stringResource(R.string.permission_for_exact_notifications),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
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
                items = AlarmsFrequency.getLabels()
            )

            SettingsListMultiSelect(
                icon = { Icon(painter = painterResource(R.drawable.question_answer),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(stringResource(R.string.additional_questionnaires),
                    color = MaterialTheme.colorScheme.secondary) },
                state = questionnaires,
                items = Questionnaire.getOptionalLabels(),
                confirmButton = stringResource(R.string.accept)
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
        }
    }
}

/**
 * Public function to read SettingsScreen using [SettingsViewModel]
 */
@Destination
@Composable
fun SettingsScreen(navigator: DestinationsNavigator, viewModel: SettingsViewModel)
{
    val alarmFrequency = viewModel.loadAlarmFrequency()
    val questionnaire = viewModel.loadQuestionnairesSelected()
    val language = viewModel.loadLanguage()
    val themeMode = viewModel.loadDarkMode()
    val dynamicColor = viewModel.loadDynamicColors()

    val context = LocalContext.current

    val onSettingsApplication : () -> Unit = { viewModel.openSettingsApplication(context) }
    val onSettingsNotifications : () -> Unit = { viewModel.openSettingsNotifications(context) }
    val onSettingsExactNotifications : () -> Unit = { viewModel.openSettingsExactNotifications(context) }

    DrawSettingsScreen(
        navigator = navigator,
        alarmFrequency = alarmFrequency,
        questionnaires = questionnaire,
        language = language,
        themeMode = themeMode,
        dynamicColor = dynamicColor,
        android12OrAbove = android12OrAbove(),
        onAlarmFrequencyChange = { viewModel.changeAlarmFrequency(it)},
        onQuestionnairesChange = { viewModel.changeQuestionnairesSelected(it) },
        onThemeChange = {theme -> viewModel.changeDarkMode(theme)},
        onDynamicChange = {dynamic -> viewModel.changeDynamicColors(dynamic)},
        onLanguageChange = { viewModel.changeLanguage(context,it)},
        onSettingsApplication = onSettingsApplication,
        onSettingsNotifications = onSettingsNotifications,
        onSettingsExactNotifications = onSettingsExactNotifications
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreview()
{
    BienestarEmocionalTheme()
    {
        DrawSettingsScreen(
            navigator = EmptyDestinationsNavigator,
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = false,
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
        DrawSettingsScreen(
            navigator = EmptyDestinationsNavigator,
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = false,
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
        DrawSettingsScreen(
            navigator = EmptyDestinationsNavigator,
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = true,
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
        DrawSettingsScreen(
            navigator = EmptyDestinationsNavigator,
            alarmFrequency = rememberIntSettingState(-1),
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = true,
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