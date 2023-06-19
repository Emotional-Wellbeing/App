package es.upm.bienestaremocional.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import es.upm.bienestaremocional.R
import es.upm.bienestaremocional.data.remote.community.CommunityResponse
import es.upm.bienestaremocional.ui.theme.BienestarEmocionalTheme

@Composable
fun CommunityRow(
    item: CommunityResponse.Data.Row,
    textBefore: String? = null
) {
    BasicCard {
        textBefore?.let { Text(text = it, textAlign = TextAlign.Center) }
        DrawPair(key = stringResource(id = R.string.depression), value = "${item.depression}")
        DrawPair(key = stringResource(id = R.string.loneliness), value = "${item.loneliness}")
        DrawPair(key = stringResource(id = R.string.stress), value = "${item.stress}")
    }
}

@Preview
@Composable
fun CommunityRowPreview() {
    BienestarEmocionalTheme {
        CommunityRow(
            item = CommunityResponse.Data.Row(
                stress = 10.0,
                depression = 15.0,
                loneliness = null
            ),
            textBefore = stringResource(id = R.string.yesterday)
        )
    }
}

@Preview
@Composable
fun CommunityRowPreviewDarkTheme() {
    BienestarEmocionalTheme(darkTheme = true) {
        CommunityRow(
            item = CommunityResponse.Data.Row(
                stress = 10.0,
                depression = 15.0,
                loneliness = null
            ),
            textBefore = stringResource(id = R.string.yesterday)
        )
    }
}