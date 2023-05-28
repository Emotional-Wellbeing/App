package es.upm.bienestaremocional.ui.screens.history

import android.util.Range
import es.upm.bienestaremocional.data.questionnaire.Questionnaire
import es.upm.bienestaremocional.utils.TimeGranularity
import java.time.LocalDate

data class HistoryState (
    val questionnaire: Questionnaire,
    val timeGranularity: TimeGranularity,
    val timeRange: Range<LocalDate>,
    val isDataNotEmpty: Boolean,
)