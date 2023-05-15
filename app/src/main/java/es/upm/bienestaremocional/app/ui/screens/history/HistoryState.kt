package es.upm.bienestaremocional.app.ui.screens.history

import android.util.Range
import es.upm.bienestaremocional.app.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.app.utils.TimeGranularity
import java.time.LocalDate

data class HistoryState (
    val questionnaire: Questionnaire,
    val timeGranularity: TimeGranularity,
    val timeRange: Range<LocalDate>,
    val isDataNotEmpty: Boolean,
)