package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.base.rememberIntSettingState
import com.alorma.compose.settings.ui.SettingsList
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.settings.AppSettings
import es.upm.bienestaremocional.app.data.settings.AppSettingsInterface
import es.upm.bienestaremocional.app.data.settings.ThemeMode
import es.upm.bienestaremocional.app.dynamicColorsSupported
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.core.ui.navigation.Screen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Renders settings menu
 */

@Composable
private fun loadDynamicColors(appSettings: AppSettingsInterface): SettingValueState<Boolean>
{
    var option : Boolean
    runBlocking(Dispatchers.IO)
    {
        option = appSettings.getDynamicColors().first()
    }
    return rememberBooleanSettingState(option)
}

private fun changeDynamicColors(option: Boolean,
                                appSettings: AppSettingsInterface,
                                coroutineScope: CoroutineScope)
{
    coroutineScope.launch {
        appSettings.saveDynamicColors(option)
    }
}

@Composable
private fun loadDarkMode(appSettings: AppSettingsInterface) : SettingValueState<Int>
{
    var option : Int
    runBlocking(Dispatchers.IO)
    {
        option = appSettings.getTheme().first().ordinal
    }
    return rememberIntSettingState(option)
}

/**
 * @Todo solve recall bug to implement restart
 */
private fun changeDarkMode(option: SettingValueState<Int>,
                           appSettings: AppSettingsInterface,
                           coroutineScope: CoroutineScope)
{
    // Default to null
    val themeMode: ThemeMode? = ThemeMode.values().getOrNull(option.value)
    themeMode?.let {
        coroutineScope.launch {
            appSettings.saveTheme(themeMode)
        }
    }
}


private fun Modifier.defualtIconModifier() = this.then(padding(all = 2.dp).size(size = 28.dp))

@Composable
fun SettingsScreen(navController: NavController, appSettings: AppSettingsInterface)
{
    val themeMode = loadDarkMode(appSettings)
    val dynamicColor = loadDynamicColors(appSettings)
    val coroutineScope = rememberCoroutineScope()

    changeDarkMode(themeMode, appSettings, coroutineScope)

    AppBasicScreen(navController = navController,
        entrySelected = LocalMenuEntry.SettingsScreen,
        label = LocalMenuEntry.SettingsScreen.labelId)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            if (dynamicColorsSupported())
            {
                SettingsSwitch(
                    icon = { Icon(painter = painterResource(R.drawable.palette),
                        contentDescription = null,
                        modifier = Modifier.defualtIconModifier()) },
                    title = { Text(text = stringResource(R.string.dynamic_colors_label),
                        color = MaterialTheme.colorScheme.secondary) },
                    subtitle = { Text(text = stringResource(R.string.dynamic_colors_description)) },
                    state = dynamicColor
                )
                {
                    changeDynamicColors(it,appSettings,coroutineScope)
                }
            }

            SettingsList(
                icon = { Icon(painter = painterResource(R.drawable.dark_mode),
                    contentDescription = null,
                    modifier = Modifier.defualtIconModifier()) },
                title = { Text(stringResource(R.string.dark_mode),
                    color = MaterialTheme.colorScheme.secondary) },
                state = themeMode,
                items = ThemeMode.getLabels()
            )

            Divider()

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.ic_baseline_help_outline),
                    contentDescription = null,
                    modifier = Modifier.defualtIconModifier()) },
                title = { Text(text = stringResource(id = R.string.about_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.about_screen_description)) },
                onClick = { navController.navigate(Screen.AboutScreen.route) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.start),
                    contentDescription = null,
                    modifier = Modifier.defualtIconModifier()) },
                title = { Text(text = stringResource(id = R.string.onboarding_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.onboarding_screen_description)) },
                onClick = { navController.navigate(Screen.OnboardingScreen.route) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.ic_baseline_people_alt),
                    contentDescription = null,
                    modifier = Modifier.defualtIconModifier()) },
                title = { Text(text = stringResource(id = R.string.credits_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.credits_screen_description)) },
                onClick = { navController.navigate(Screen.CreditsScreen.route) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview()
{
    val navController = rememberNavController()

    val appSettings = AppSettings(context = LocalContext.current)

    BienestarEmocionalTheme()
    {
        SettingsScreen(navController, appSettings)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewDarkTheme()
{
    val navController = rememberNavController()

    val appSettings = AppSettings(context = LocalContext.current)

    BienestarEmocionalTheme(darkTheme = true)
    {
        SettingsScreen(navController,appSettings)
    }
}