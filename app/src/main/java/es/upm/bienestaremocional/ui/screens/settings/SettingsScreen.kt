package es.upm.bienestaremocional.ui.screens.settings

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
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
import es.upm.bienestaremocional.data.Measure
import es.upm.bienestaremocional.data.settings.ThemeMode
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.screens.destinations.AboutScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.CreditsScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.MyDataScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.OnboardingScreenDestination
import es.upm.bienestaremocional.ui.screens.destinations.PrivacyPolicyScreenDestination
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme
import es.upm.bienestaremocional.utils.android12OrAbove
import es.upm.bienestaremocional.utils.getActivity
import es.upm.bienestaremocional.utils.openForeignActivity
import es.upm.bienestaremocional.utils.restartApp
import kotlinx.coroutines.launch

/**
 * Public function to read SettingsScreen using [SettingsViewModel]
 */
@Destination
@Composable
fun SettingsScreen(
    navigator: DestinationsNavigator,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val restartToApplyChanges = stringResource(id = R.string.restart_apply_changes)
    val restartApplyAllChanges = stringResource(id = R.string.restart_apply_all_changes)
    val actionLabel = stringResource(id = R.string.restart)

    val questionnaire = rememberIntSetSettingState(viewModel.loadMeasuresSelected())
    val language = rememberIntSettingState(viewModel.loadLanguage())
    val themeMode = rememberIntSettingState(viewModel.loadDarkMode())
    val dynamicColor = rememberBooleanSettingState(viewModel.loadDynamicColors())

    val android12OrAbove = android12OrAbove()
    val languagesAvailable = viewModel.getLanguagesAvailable()

    val onMeasuresChange: (Set<Int>) -> Unit = { viewModel.changeMeasuresSelected(it) }
    val onLanguageChange: (Int) -> Unit = { lang ->
        viewModel.changeLanguage(context, lang)
        coroutineScope.launch {
            showRestartInfo(
                snackbarHostState = snackbarHostState,
                message = restartApplyAllChanges,
                actionLabel = actionLabel,
                context = context
            )
        }
    }
    val onThemeChange: (Int) -> Unit = { theme ->
        viewModel.changeDarkMode(theme)
        coroutineScope.launch {
            showRestartInfo(
                snackbarHostState = snackbarHostState,
                message = restartToApplyChanges,
                actionLabel = actionLabel,
                context = context
            )
        }
    }
    val onDynamicChange: (Boolean) -> Unit = { dynamic ->
        viewModel.changeDynamicColors(dynamic)
        coroutineScope.launch {
            showRestartInfo(
                snackbarHostState = snackbarHostState,
                message = restartToApplyChanges,
                actionLabel = actionLabel,
                context = context
            )
        }
    }
    val onSettingsApplication = { viewModel.openSettingsApplication(context) }
    val onSettingsNotifications = { viewModel.openSettingsNotifications(context) }

    SettingsScreen(
        navigator = navigator,
        snackbarHostState = snackbarHostState,
        questionnaires = questionnaire,
        language = language,
        themeMode = themeMode,
        dynamicColor = dynamicColor,
        android12OrAbove = android12OrAbove,
        languagesAvailable = languagesAvailable,
        onMeasuresChange = onMeasuresChange,
        onLanguageChange = onLanguageChange,
        onThemeChange = onThemeChange,
        onDynamicChange = onDynamicChange,
        onSettingsApplication = onSettingsApplication,
        onSettingsNotifications = onSettingsNotifications,
    )
}

/**
 * Renders settings menu
 * @param navigator: needed for render menu
 * @param questionnaires: var that stores additional questionnaires selection
 * @param language: var that stores the language of the app
 * @param themeMode: var that stores theme setting value
 * @param dynamicColor: var that stores dynamic setting value
 * @param android12OrAbove: boolean to print Android 12+ options
 * @param languagesAvailable: list with the languages supported by the app
 * @param onMeasuresChange: callback to react questionnaires changes
 * @param onLanguageChange: callback to react language setting changes
 * @param onThemeChange: callback to react theme setting changes
 * @param onDynamicChange: callback to react dynamic setting changes
 * @param onSettingsApplication: executed when user press application's setting
 */
@Composable
private fun SettingsScreen(
    navigator: DestinationsNavigator,
    snackbarHostState: SnackbarHostState,
    questionnaires: SettingValueState<Set<Int>>,
    language: SettingValueState<Int>,
    themeMode: SettingValueState<Int>,
    dynamicColor: SettingValueState<Boolean>,
    android12OrAbove: Boolean,
    languagesAvailable: List<String>,
    onMeasuresChange: (Set<Int>) -> Unit,
    onLanguageChange: (Int) -> Unit,
    onThemeChange: (Int) -> Unit,
    onDynamicChange: (Boolean) -> Unit,
    onSettingsApplication: () -> Unit,
    onSettingsNotifications: () -> Unit,
) {
    val context = LocalContext.current
    val measureOptions: List<String> = Measure.getOptionalLabels()

    AppBasicScreen(
        navigator = navigator,
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
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.storage),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.my_data_label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(id = R.string.my_data_description)) },
                onClick = { navigator.navigate(MyDataScreenDestination()) },
            )


            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.security),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.privacy_policy_screen_label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(id = R.string.privacy_policy_screen_description)) },
                onClick = { navigator.navigate(PrivacyPolicyScreenDestination) },
            )

            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.health_connect_logo),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier(),
                        tint = Color.Unspecified
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.health_connect_settings_label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(id = R.string.health_connect_settings_description)) },
                onClick = {
                    openForeignActivity(
                        context = context,
                        action = ACTION_HEALTH_CONNECT_SETTINGS
                    )
                },
            )

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.ui_group)

            SettingsList(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.language),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        stringResource(R.string.language),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                state = language,
                items = languagesAvailable,
                onItemSelected = { index, _ -> onLanguageChange(index) }
            )

            if (android12OrAbove) {
                SettingsSwitch(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.palette),
                            contentDescription = null,
                            modifier = Modifier.defaultIconModifier()
                        )
                    },
                    title = {
                        Text(
                            text = stringResource(R.string.dynamic_colors_label),
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    subtitle = { Text(text = stringResource(R.string.dynamic_colors_description)) },
                    state = dynamicColor,
                    onCheckedChange = { onDynamicChange(it) }
                )
            }

            SettingsList(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.dark_mode),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        stringResource(R.string.dark_mode),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                state = themeMode,
                items = ThemeMode.getLabels(),
                onItemSelected = { index, _ -> onThemeChange(index) }
            )


            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.notifications)

            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.settings_applications),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        stringResource(R.string.application_settings),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(R.string.application_settings_label)) },
                onClick = onSettingsApplication,
            )

            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.notifications),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        stringResource(R.string.permission_for_notifications),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(R.string.permission_for_notifications_body)) },
                onClick = onSettingsNotifications,
            )

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.feedback_group)

            SettingsListMultiSelect(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.question_answer),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        stringResource(R.string.additional_measures),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                state = questionnaires,
                items = measureOptions,
                confirmButton = stringResource(R.string.accept),
                onItemsSelected = { items ->
                    val indexes = items
                        .map { item -> measureOptions.indexOf(item) }
                        .filter { it >= 0 }
                    onMeasuresChange(indexes.toSet())
                }
            )

            HorizontalDivider(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp))

            GroupText(textRes = R.string.misc_group)

            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.help_outline),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.about_screen_label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(id = R.string.about_screen_description)) },
                onClick = { navigator.navigate(AboutScreenDestination) },
            )

            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.start),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.onboarding_screen_label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(id = R.string.onboarding_screen_description)) },
                onClick = { navigator.navigate(OnboardingScreenDestination(standalone = true)) },
            )

            SettingsMenuLink(
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.people_alt),
                        contentDescription = null,
                        modifier = Modifier.defaultIconModifier()
                    )
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.credits_screen_label),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                subtitle = { Text(stringResource(id = R.string.credits_screen_description)) },
                onClick = { navigator.navigate(CreditsScreenDestination) },
            )

        }
    }
}

/**
 * Text with custom style to split group of settings
 */
@Composable
private fun GroupText(@StringRes textRes: Int) =
    Text(
        text = stringResource(textRes),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, bottom = 16.dp),
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary
    )

/**
 * Default icon padding and size for this screen
 */
private fun Modifier.defaultIconModifier() = this.then(padding(all = 2.dp).size(size = 28.dp))

/**
 * Show snackbar to restart app in order to show properly user's changes
 */

private suspend fun showRestartInfo(
    snackbarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    context: Context
) {
    val result = snackbarHostState.showSnackbar(
        message = message, actionLabel = actionLabel,
        withDismissAction = true, duration = SnackbarDuration.Long
    )
    if (result === SnackbarResult.ActionPerformed)
        restartApp(activity = context.getActivity())
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreview() {
    BienestarEmocionalTheme()
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState = remember { SnackbarHostState() },
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = false,
            languagesAvailable = listOf(),
            onMeasuresChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState = remember { SnackbarHostState() },
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = false,
            languagesAvailable = listOf(),
            onMeasuresChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    BienestarEmocionalTheme()
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState = remember { SnackbarHostState() },
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = true,
            languagesAvailable = listOf(),
            onMeasuresChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(
            navigator = EmptyDestinationsNavigator,
            snackbarHostState = remember { SnackbarHostState() },
            questionnaires = rememberIntSetSettingState(),
            language = rememberIntSettingState(-1),
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            android12OrAbove = true,
            languagesAvailable = listOf(),
            onMeasuresChange = {},
            onThemeChange = {},
            onDynamicChange = {},
            onLanguageChange = {},
            onSettingsApplication = {},
            onSettingsNotifications = {},
        )
    }
}