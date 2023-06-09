package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffDepressionManager
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.ui.screens.destinations.OneOffDepressionScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class OneOffDepressionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyStressRepository: OneOffDepressionRepository,
    dailyStressManager: OneOffDepressionManager,
    ) : ScoredQuestionnaireViewModel(
    repository = dailyStressRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailyStressManager as ScoredManager<MeasureEntity>,
    entityId = OneOffDepressionScreenDestination.argsFrom(savedStateHandle).entityId,
)