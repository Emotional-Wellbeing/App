package es.upm.bienestaremocional.ui.screens.questionnaire

import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository

abstract class ScoredQuestionnaireViewModel(
    repository: QuestionnaireRepository<MeasureEntity>,
    override val manager: ScoredManager<MeasureEntity>,
    entityId: Long,
) : NotScoredQuestionnaireViewModel(
    repository = repository,
    manager = manager,
    entityId = entityId
)
{
    val score
        get() = manager.score
}