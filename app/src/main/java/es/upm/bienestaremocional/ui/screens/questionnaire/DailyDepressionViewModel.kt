package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyDepressionManager
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.destinations.DailyDepressionScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class DailyDepressionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyDepressionRepository: DailyDepressionRepository,
    dailyDepressionManager: DailyDepressionManager,
    ) : ScoredQuestionnaireViewModel(
    repository = dailyDepressionRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailyDepressionManager as ScoredManager<MeasureEntity>,
    entityId = DailyDepressionScreenDestination.argsFrom(savedStateHandle).entityId,
)