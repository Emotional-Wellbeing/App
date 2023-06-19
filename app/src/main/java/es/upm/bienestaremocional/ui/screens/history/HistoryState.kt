package es.upm.bienestaremocional.ui.screens.history

import android.util.Range
import es.upm.bienestaremocional.data.questionnaire.daily.DailyScoredQuestionnaire
import es.upm.bienestaremocional.utils.TimeGranularity
import java.time.ZonedDateTime

data class HistoryState(
    val questionnaire: DailyScoredQuestionnaire,
    val timeGranularity: TimeGranularity,
    val timeRange: Range<ZonedDateTime>,
    val isDataNotEmpty: Boolean,
)