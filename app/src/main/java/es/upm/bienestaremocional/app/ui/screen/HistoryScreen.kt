package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.ui.navigation.MenuEntry
import es.upm.bienestaremocional.app.ui.viewmodel.HistoryViewModel
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard

/**
 * Plots graphics about user's history
 */
@Destination
@Composable
fun HistoryScreen(navigator: DestinationsNavigator, historyViewModel: HistoryViewModel)
{
    // State
    val questionnaireRounds = historyViewModel.questionnaireRounds.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchQuestionnaireRounds()
    }



    AppBasicScreen(navigator = navigator,
        entrySelected = MenuEntry.HistoryScreen,
        label = MenuEntry.HistoryScreen.labelId)
    {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            BasicCard{
                Text("History placeholder")
            }
            Spacer(Modifier.size(16.dp))
            // UI
            LazyColumn {
                questionnaireRounds.value?.let { list ->
                    items(list.size) { index ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Ronda:")
                            Text("Id: ${list[index].questionnaireRound.id}")
                            Text("Creado en : ${list[index].questionnaireRound.createdAt}")
                            Text(text = "PSS:")
                            Text("Id: ${list[index].pss.id}")
                            Text("Creado en : ${list[index].pss.createdAt}")
                            Text("Score: ${list[index].pss.score}")
                            list[index].phq?.let {
                                Text(text = "PHQ:")
                                Text("Id: ${it.id}")
                                Text("Creado en : ${it.createdAt}")
                                Text("Score: ${it.score}")
                            }

                            list[index].ucla?.let {
                                Text(text = "UCLA:")
                                Text("Id: ${it.id}")
                                Text("Creado en : ${it.createdAt}")
                                Text("Score: ${it.score}")
                            }
                        }
                        Spacer(Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}


/*
@Preview(
    showBackground = true,
    group = "Light Theme"
)
@Composable
fun HistoryScreenPreview()
{
    BienestarEmocionalTheme {
        HistoryScreen(EmptyDestinationsNavigator)
    }
}

@Preview(
    showBackground = true,
    group = "Dark Theme"
)
@Composable
fun HistoryScreenPreviewDarkTheme()
{
    BienestarEmocionalTheme(darkTheme = true) {
        HistoryScreen(EmptyDestinationsNavigator)
    }
}*/