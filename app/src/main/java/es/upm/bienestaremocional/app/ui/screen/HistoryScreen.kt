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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.viewmodel.HistoryViewModel
import es.upm.bienestaremocional.app.utils.formatUnixTimeStamp
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen
import es.upm.bienestaremocional.core.ui.component.BasicCard

/**
 * Plots graphics about user's history
 */
@Destination
@Composable
fun HistoryScreen(navigator: DestinationsNavigator,
                  historyViewModel: HistoryViewModel = hiltViewModel()
)
{
    // State
    val questionnaireRounds = historyViewModel.questionnaireRounds.observeAsState()

    // API call
    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchIncompletedQuestionnaireRounds()
    }



    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HistoryScreen,
        label = BottomBarDestination.HistoryScreen.label)
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

                        Card(modifier = Modifier.fillMaxWidth())
                        {
                            Text(text = "Ronda: ${list[index].questionnaireRound.id}")
                            Text("Creado en : ${formatUnixTimeStamp(list[index].questionnaireRound.createdAt)}")
                            Text("Incompletos: ")
                            if (!list[index].pss.completed)
                            {
                                Text(text = "PSS:")
                                Text("Id: ${list[index].pss.id}")
                                Text("Creado en : ${formatUnixTimeStamp(list[index].pss.createdAt)}")
                                Text("Score: ${list[index].pss.score}")
                            }

                            list[index].phq?.let {
                                if (!it.completed)
                                {
                                    Text(text = "PHQ:")
                                    Text("Id: ${it.id}")
                                    Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                    Text("Score: ${it.score}")
                                }
                            }

                            list[index].ucla?.let {
                                if (!it.completed)
                                {
                                    Text(text = "UCLA:")
                                    Text("Id: ${it.id}")
                                    Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                    Text("Score: ${it.score}")
                                }
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