package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.ui.SettingsMenuLink
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.navigation.LocalMenuEntry
import es.upm.bienestaremocional.core.ui.navigation.Screen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Renders settings menu
 */

@Composable
fun SettingsScreen(navController: NavController)
{
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
            /*SettingsSwitch (
                title = { Text(text = "Hello 3") },
                onCheckedChange = {})

            Divider()*/

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.ic_baseline_help_outline),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .size(size = 28.dp)) },
                title = { Text(text = stringResource(id = R.string.about_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.about_screen_description)) },
                onClick = { navController.navigate(Screen.AboutScreen.route) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.start),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .size(size = 28.dp)) },
                title = { Text(text = stringResource(id = R.string.onboarding_screen_label),
                    color = MaterialTheme.colorScheme.secondary) },
                subtitle = { Text(stringResource(id = R.string.onboarding_screen_description)) },
                onClick = { navController.navigate(Screen.OnboardingScreen.route) },
            )

            SettingsMenuLink(
                icon = { Icon(painter = painterResource(R.drawable.ic_baseline_people_alt),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(all = 2.dp)
                        .size(size = 28.dp)) },
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
    BienestarEmocionalTheme {
        SettingsScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreviewDarkTheme()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme(darkTheme = true) {
        SettingsScreen(navController)
    }
}