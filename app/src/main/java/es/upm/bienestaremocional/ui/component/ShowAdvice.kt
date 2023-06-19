package es.upm.bienestaremocional.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.Advice
import es.upm.bienestaremocional.destinations.AdviceScreenDestination

@Composable
fun ShowAdviceHeadline(
    navigator: DestinationsNavigator,
    advice: Advice,
    verticalArrangement: Arrangement.Vertical,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = advice.head),
            textAlign = TextAlign.Justify,
            style = textStyle
        )
        advice.body?.let {
            TextButton(onClick = {
                navigator.navigate(
                    AdviceScreenDestination(
                        advice = advice
                    )
                )
            }) {
                Text(text = stringResource(id = R.string.view_more_information))
            }
        }
    }
}