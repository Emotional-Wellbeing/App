package es.upm.bienestaremocional.app.ui.screens.credits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Contains info about people involved in the app
 */

@Destination
@Composable
fun CreditsScreen(navigator: DestinationsNavigator,
                  viewModel: CreditsViewModel = hiltViewModel()
)
{
    CreditsScreen(navigator = navigator,
        widthSize = computeWindowWidthSize(),
        importantCredits = viewModel.importantPeople,
        notImportantCredits = viewModel.notImportantPeople)
}

@Composable
private fun CreditsScreen(navigator: DestinationsNavigator,
                          widthSize: WindowWidthSizeClass,
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            importantCredits.forEach {
                CreditComponent(credit = it, widthSize = widthSize)
            }
            Divider()
            notImportantCredits.forEach {
                CreditComponent(credit = it, widthSize = widthSize)
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
            widthSize = WindowWidthSizeClass.Compact,
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
            widthSize = WindowWidthSizeClass.Compact,
            importantCredits = CreditContent.content.filter { credit -> credit.importantContribution },
            notImportantCredits = CreditContent.content.filter { credit -> !credit.importantContribution },
        )
    }
}