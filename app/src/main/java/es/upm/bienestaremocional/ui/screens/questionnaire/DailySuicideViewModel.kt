package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.NotScoredManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailySuicideManager
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.ui.screens.destinations.DailySuicideScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class DailySuicideViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    dailySuicideRepository: DailySuicideRepository,
    dailySuicideManager: DailySuicideManager,
    ) : NotScoredQuestionnaireViewModel(
    repository = dailySuicideRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailySuicideManager as NotScoredManager<MeasureEntity>,
    entityId = DailySuicideScreenDestination.argsFrom(savedStateHandle).entityId,
)