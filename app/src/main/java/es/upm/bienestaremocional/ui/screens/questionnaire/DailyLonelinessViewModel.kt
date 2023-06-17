package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyLonelinessManager
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.ui.screens.destinations.DailyLonelinessScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class DailyLonelinessViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyLonelinessRepository: DailyLonelinessRepository,
    private val dailyLonelinessManager: DailyLonelinessManager,
    ) : ScoredQuestionnaireViewModel(
    repository = dailyLonelinessRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailyLonelinessManager as ScoredManager<MeasureEntity>,
    entityId = DailyLonelinessScreenDestination.argsFrom(savedStateHandle).entityId,
)