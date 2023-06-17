package es.upm.bienestaremocional.ui.screens.questionnaire

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffLonelinessManager
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository
import es.upm.bienestaremocional.ui.screens.destinations.OneOffLonelinessScreenDestination
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
@HiltViewModel
class OneOffLonelinessViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val dailyStressRepository: OneOffLonelinessRepository,
    dailyStressManager: OneOffLonelinessManager,
) : ScoredQuestionnaireViewModel(
    repository = dailyStressRepository as QuestionnaireRepository<MeasureEntity>,
    manager = dailyStressManager as ScoredManager<MeasureEntity>,
    entityId = OneOffLonelinessScreenDestination.argsFrom(savedStateHandle).entityId,
)