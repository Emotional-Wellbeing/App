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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.app.data.credits.CreditContent
import es.upm.bienestaremocional.app.ui.component.CreditComponent
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.theme.BienestarEmocionalTheme

/**
 * Contains info about people involved in the app
 */

@Composable
fun CreditsScreen(navController: NavController)
{
    AppBasicScreen(navController = navController,
        entrySelected = null,
        label = R.string.credits_screen_label)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            LazyColumn()
            {
                items(CreditContent.content.filter { credit -> credit.importantContribution })
                {
                    CreditComponent(credit = it)
                    Spacer(Modifier.size(16.dp))
                }
                item {
                    Divider()
                }
                items(CreditContent.content.filter { credit -> !credit.importantContribution })
                {
                    CreditComponent(credit = it)
                    Spacer(Modifier.size(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreditsScreenPreview()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme {
        CreditsScreen(navController)
    }
}

@Preview(showBackground = true)
@Composable
fun CreditsScreenPreviewDarkTheme()
{
    val navController = rememberNavController()
    BienestarEmocionalTheme(darkTheme = true) {
        CreditsScreen(navController)
    }
}