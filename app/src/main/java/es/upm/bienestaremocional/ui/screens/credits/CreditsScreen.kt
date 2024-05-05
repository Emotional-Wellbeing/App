package es.upm.bienestaremocional.ui.screens.credits

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
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
import es.upm.bienestaremocional.data.credits.Credit
import es.upm.bienestaremocional.data.credits.CreditContent
import es.upm.bienestaremocional.ui.component.AppBasicScreen
import es.upm.bienestaremocional.ui.component.CreditComponent
import es.upm.bienestaremocional.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.ui.responsive.computeWindowHeightSize
import es.upm.bienestaremocional.ui.responsive.computeWindowWidthSize
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

/**
 * Contains info about people involved in the app
 */

@Destination
@Composable
fun CreditsScreen(
    navigator: DestinationsNavigator,
    viewModel: CreditsViewModel = hiltViewModel()
) {
    CreditsScreen(
        navigator = navigator,
        widthSize = computeWindowWidthSize(),
        heightSize = computeWindowHeightSize(),
        importantCredits = viewModel.importantPeople,
        notImportantCredits = viewModel.notImportantPeople
    )
}

@Composable
private fun CreditsScreen(
    navigator: DestinationsNavigator,
    widthSize: WindowWidthSizeClass,
    heightSize: WindowHeightSizeClass,
    importantCredits: List<Credit>,
    notImportantCredits: List<Credit>
) {
    AppBasicScreen(
        navigator = navigator,
        entrySelected = BottomBarDestination.SettingsScreen,
        label = R.string.credits_screen_label
    )
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        )
        {
            importantCredits.forEach {
                CreditComponent(
                    credit = it,
                    widthSize = widthSize,
                    heightSize = heightSize
                )
            }
            HorizontalDivider()
            notImportantCredits.forEach {
                CreditComponent(
                    credit = it,
                    widthSize = widthSize,
                    heightSize = heightSize
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditsScreenPreviewCompactScreen() {

    BienestarEmocionalTheme {
        CreditsScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
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
fun CreditsScreenPreviewDarkThemeCompactScreen() {

    BienestarEmocionalTheme(darkTheme = true) {
        CreditsScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Compact,
            heightSize = WindowHeightSizeClass.Compact,
            importantCredits = CreditContent.content.filter { credit -> credit.importantContribution },
            notImportantCredits = CreditContent.content.filter { credit -> !credit.importantContribution },
        )
    }
}

@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun CreditsScreenPreviewMediumScreen() {

    BienestarEmocionalTheme {
        CreditsScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Medium,
            heightSize = WindowHeightSizeClass.Medium,
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
fun CreditsScreenPreviewDarkThemeMediumScreen() {

    BienestarEmocionalTheme(darkTheme = true) {
        CreditsScreen(
            navigator = EmptyDestinationsNavigator,
            widthSize = WindowWidthSizeClass.Medium,
            heightSize = WindowHeightSizeClass.Medium,
            importantCredits = CreditContent.content.filter { credit -> credit.importantContribution },
            notImportantCredits = CreditContent.content.filter { credit -> !credit.importantContribution },
        )
    }
}