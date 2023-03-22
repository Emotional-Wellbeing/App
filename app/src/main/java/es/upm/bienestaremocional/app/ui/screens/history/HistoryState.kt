package es.upm.bienestaremocional.app.ui.screens.history

import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire

data class HistoryState (
    val questionnaire: Questionnaire,
    val timeGranularity: TimeGranularity,
    val scores: List<Int>
)