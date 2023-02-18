package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.credits.Credit
import es.upm.bienestaremocional.app.data.credits.CreditContent
import es.upm.bienestaremocional.app.ui.component.CreditComponent
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.viewmodel.CreditsViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.responsive.WindowSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Contains info about people involved in the app
 */

@Destination
@Composable
fun CreditsScreen(navigator: DestinationsNavigator,
                  windowSize: WindowSize,
                  viewModel: CreditsViewModel = hiltViewModel()
)
{
    CreditsScreen(navigator = navigator,
        windowSize = windowSize,
        importantCredits = viewModel.importantPeople,
        notImportantCredits = viewModel.notImportantPeople)
}

@Composable
private fun CreditsScreen(navigator: DestinationsNavigator,
                          windowSize: WindowSize,
                          importantCredits: List<Credit>,
                          notImportantCredits: List<Credit>
)
{
    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.credits_screen_label)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            LazyColumn()
            {
                items(importantCredits)
                {
                    CreditComponent(credit = it, windowSize = windowSize)
                    Spacer(Modifier.size(16.dp))
                }
                item {
                    Divider()
                }
                items(notImportantCredits)
                {
                    CreditComponent(credit = it, windowSize = windowSize)
                    Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditsScreenPreview()
{

    BienestarEmocionalTheme {
        CreditsScreen(navigator = EmptyDestinationsNavigator,
            windowSize = WindowSize.COMPACT,
            importantCredits = CreditContent.content.filter { credit -> credit.importantContribution },
            notImportantCredits = CreditContent.content.filter { credit -> !credit.importantContribution },
        )
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun CreditsScreenPreviewDarkTheme()
{

    BienestarEmocionalTheme(darkTheme = true) {
        CreditsScreen(navigator = EmptyDestinationsNavigator,
            windowSize = WindowSize.COMPACT,
            importantCredits = CreditContent.content.filter { credit -> credit.importantContribution },
            notImportantCredits = CreditContent.content.filter { credit -> !credit.importantContribution },
        )
    }
}