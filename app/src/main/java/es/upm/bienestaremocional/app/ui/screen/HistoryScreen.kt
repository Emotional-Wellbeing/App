package es.upm.bienestaremocional.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRoundReduced
import es.upm.bienestaremocional.app.ui.navigation.BottomBarDestination
import es.upm.bienestaremocional.app.ui.screen.destinations.QuestionnaireRoundScreenDestination
import es.upm.bienestaremocional.app.ui.viewmodel.HistoryViewModel
import es.upm.bienestaremocional.app.utils.formatUnixTimeStamp
import es.upm.bienestaremocional.core.ui.component.AppBasicScreen

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
    val questionnaireRoundsIncompleted = historyViewModel.questionnaireRoundsIncompleted.observeAsState()
    val questionnaireRounds = historyViewModel.questionnaireRounds.observeAsState()

    var showColumn by remember { mutableStateOf(true) }
    lateinit var questionnaireRoundReduced : QuestionnaireRoundReduced

    // API call
    LaunchedEffect(key1 = Unit) {
        historyViewModel.fetchIncompletedQuestionnaireRounds()
        historyViewModel.fetchAllQuestionnaireRounds()
    }



    AppBasicScreen(navigator = navigator,
        entrySelected = BottomBarDestination.HistoryScreen,
        label = BottomBarDestination.HistoryScreen.label)
    {
        if (showColumn)
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                // UI
                Text(text = "Incompletos:")
                questionnaireRoundsIncompleted.value?.let { list ->
                    list.forEach { element ->
                        Card(modifier = Modifier.fillMaxWidth())
                        {
                            Text(text = "Ronda: ${element.questionnaireRound.id}")
                            Text("Creado en : ${formatUnixTimeStamp(element.questionnaireRound.createdAt)}")
                            Text("Modificado en : ${formatUnixTimeStamp(element.questionnaireRound.modifiedAt)}")
                            Text("Incompletos: ")
                            if (!element.pss.completed)
                            {
                                Text(text = "PSS:")
                                Text("Id: ${element.pss.id}")
                                Text("Creado en : ${formatUnixTimeStamp(element.pss.createdAt)}")
                                Text("Score: ${element.pss.score}")
                            }

                            element.phq?.let {
                                if (!it.completed)
                                {
                                    Text(text = "PHQ:")
                                    Text("Id: ${it.id}")
                                    Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                    Text("Score: ${it.score}")
                                }
                            }

                            element.ucla?.let {
                                if (!it.completed)
                                {
                                    Text(text = "UCLA:")
                                    Text("Id: ${it.id}")
                                    Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                    Text("Score: ${it.score}")
                                }
                            }

                            TextButton(onClick = {
                                questionnaireRoundReduced = QuestionnaireRoundReduced(
                                    element.questionnaireRound.id,
                                    if(element.pss.completed) null else element.pss.id,
                                    element.phq?.let { if (it.completed) null else it.id },
                                    element.ucla?.let { if (it.completed) null else it.id },
                                )
                                showColumn = false}) {
                                Text("retomar", color = MaterialTheme.colorScheme.tertiary)
                            }
                        }
                    }
                }
                //}

                Text(text = "Todos:")
                //LazyColumn {
                questionnaireRounds.value?.let { list ->
                    list.forEach { element ->

                        Card(modifier = Modifier.fillMaxWidth())
                        {
                            Text(text = "Ronda: ${element.questionnaireRound.id}")
                            Text("Creado en : ${formatUnixTimeStamp(element.questionnaireRound.createdAt)}")
                            element.pss.let {
                                Text(text = "PSS:")
                                Text("Id: ${element.pss.id}")
                                Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                Text("Modificado en : ${formatUnixTimeStamp(it.modifiedAt)}")
                                Text("Score: ${it.score}")
                            }

                            element.phq?.let {
                                Text(text = "PHQ:")
                                Text("Id: ${it.id}")
                                Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                Text("Modificado en : ${formatUnixTimeStamp(it.modifiedAt)}")
                                Text("Score: ${it.score}")
                            }

                            element.ucla?.let {
                                Text(text = "UCLA:")
                                Text("Id: ${it.id}")
                                Text("Creado en : ${formatUnixTimeStamp(it.createdAt)}")
                                Text("Modificado en : ${formatUnixTimeStamp(it.modifiedAt)}")
                                Text("Score: ${it.score}")
                            }
                        }
                    }
                }
            }
        }
        else
        {
            navigator.navigate(QuestionnaireRoundScreenDestination(questionnaireRoundReduced = questionnaireRoundReduced))
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