package es.upm.sistemabienestaremocional.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upm.sistemabienestaremocional.R
import es.upm.sistemabienestaremocional.component.BasicCard
import es.upm.sistemabienestaremocional.component.DetailScreen
import es.upm.sistemabienestaremocional.theme.SistemaBienestarEmocionalTheme

/**
 * Plots graphics about user's history
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HistoryScreen(onBackClick : () -> Unit)
{
    DetailScreen(
        idTitle = R.string.history_screen_label,
        onBackClick = onBackClick)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            BasicCard{
                    Text("History placeholder")
            }
        }
    }
}

@Preview
@Composable
fun HistoryScreenPreview()
{
    SistemaBienestarEmocionalTheme {
        HistoryScreen(onBackClick = {})
    }
}