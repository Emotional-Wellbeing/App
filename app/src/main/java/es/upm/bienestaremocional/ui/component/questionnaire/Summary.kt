package es.upm.bienestaremocional.ui.component.questionnaire

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.ui.component.OneOffStressStatus
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun Summary(
    content : @Composable () -> Unit,
    onSuccess : () -> Unit
)
{
    Surface(modifier = Modifier.fillMaxSize())
    {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Card(modifier = Modifier.padding(16.dp))
            {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    content()
                    OutlinedButton(onClick = onSuccess)
                    {
                        Text(stringResource(R.string.continue_word))
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ShowSummaryPreview()
{
    val summaryContent : @Composable () -> Unit = {
        OneOffStressStatus(
            navigator = EmptyDestinationsNavigator,
            data = 10,
            widthSize = WindowWidthSizeClass.Compact
        )
    }

    BienestarEmocionalTheme {
        Summary(
            content = summaryContent,
            onSuccess = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ShowSummaryDarkThemePreview()
{
    val summaryContent : @Composable () -> Unit = {
        OneOffStressStatus(
            navigator = EmptyDestinationsNavigator,
            data = 10,
            widthSize = WindowWidthSizeClass.Compact
        )
    }

    BienestarEmocionalTheme(darkTheme = true) {
        Summary(
            content = summaryContent,
            onSuccess = {}
        )
    }
}