package es.upm.bienestaremocional.app.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.base.rememberIntSettingState
import com.alorma.compose.settings.ui.SettingsList
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.dynamicColorsSupported
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.core.ui.navigation.Screen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme


private fun Modifier.defaultIconModifier() = this.then(padding(all = 2.dp).size(size = 28.dp))

private suspend fun showRestartInfo(snackbarHostState: SnackbarHostState,
                                    message : String)
{
    snackbarHostState.showSnackbar(message = message)
}

/**
 * Renders settings menu
 * @param navController: needed for render menu
 * @param themeMode: var that stores theme setting value
 * @param dynamicColor: var that stores dynamic setting value
 * @param shouldDisplayDynamicOption: boolean to control rendering (or not) dynamic option
 * (option available in Android 12+)
 * @param onThemeChange: callback to react theme setting changes
 * @param onDynamicChange: callback to react dynamic setting changes
 */
@Composable
private fun SettingsScreen(navController: NavController,
                   themeMode: SettingValueState<Int>,
                   dynamicColor : SettingValueState<Boolean>,
                   shouldDisplayDynamicOption : Boolean,
                   onThemeChange : suspend (SettingValueState<Int>) -> Unit,
                   onDynamicChange : suspend (SettingValueState<Boolean>) -> Unit)
{
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    //avoid undesired launch
    val defaultThemeValue : Int = remember { themeMode.value }
    val defaultDynamicValue : Boolean = remember { dynamicColor.value }

    val snackbarTextToDisplay = stringResource(id = R.string.restart_apply_changes)

    if (themeMode.value != defaultThemeValue)
    {
        LaunchedEffect(themeMode.value)
        {
            onThemeChange(themeMode)
            showRestartInfo(snackbarHostState,snackbarTextToDisplay)
        }
    }

    if (dynamicColor.value != defaultDynamicValue)
    {
        LaunchedEffect(dynamicColor.value)
        {
            onDynamicChange(dynamicColor)
            showRestartInfo(snackbarHostState,snackbarTextToDisplay)
        }
    }


    AppBasicScreen(navController = navController,
        entrySelected = LocalMenuEntry.SettingsScreen,
        label = LocalMenuEntry.SettingsScreen.labelId,
        scope = scope,
        snackbarHostState = snackbarHostState
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            if (shouldDisplayDynamicOption)
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

            Divider()

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.help_outline),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.about_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.about_screen_description)) },
                onClick = { navController.navigate(Screen.AboutScreen.route) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.start),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.onboarding_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.onboarding_screen_description)) },
                onClick = { navController.navigate(Screen.OnboardingScreen.route) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.people_alt),
                    contentDescription = null,
                    modifier = Modifier.defaultIconModifier()) },
                title = { Text(text = stringResource(id = R.string.credits_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.credits_screen_description)) },
                onClick = { navController.navigate(Screen.CreditsScreen.route) },
            )
        }
    }
}

/**
 * Public function to read SettingsScreen using [SettingsViewModel]
 */
@Composable
fun SettingsScreenWrapper(navController: NavController)
{
    val viewModel : SettingsViewModel = viewModel(factory = SettingsViewModel.Factory)
    val themeMode = viewModel.loadDarkMode()
    val dynamicColor = viewModel.loadDynamicColors()

    SettingsScreen(
        navController = navController,
        themeMode = themeMode,
        dynamicColor = dynamicColor,
        shouldDisplayDynamicOption = dynamicColorsSupported(),
        onThemeChange = {theme -> viewModel.changeDarkMode(theme)},
        onDynamicChange = {dynamic -> viewModel.changeDynamicColors(dynamic)}
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreview()
{
    val navController = rememberNavController()

    BienestarEmocionalTheme()
    {
        SettingsScreen(
            navController = navController,
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            shouldDisplayDynamicOption = false,
            onThemeChange = {},
            onDynamicChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenNoDynamicPreviewDarkTheme()
{
    val navController = rememberNavController()

    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(
            navController = navController,
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            shouldDisplayDynamicOption = false,
            onThemeChange = {},
            onDynamicChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview()
{
    val navController = rememberNavController()

    BienestarEmocionalTheme()
    {
        SettingsScreen(
            navController = navController,
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            shouldDisplayDynamicOption = true,
            onThemeChange = {},
            onDynamicChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewDarkTheme()
{
    val navController = rememberNavController()

    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(
            navController = navController,
            themeMode = rememberIntSettingState(-1),
            dynamicColor = rememberBooleanSettingState(true),
            shouldDisplayDynamicOption = true,
            onThemeChange = {},
            onDynamicChange = {}
        )
    }
}