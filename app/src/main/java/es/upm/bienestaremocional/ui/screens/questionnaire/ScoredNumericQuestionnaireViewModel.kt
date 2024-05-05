package es.upm.bienestaremocional.ui.screens.questionnaire

import es.upm.bienestaremocional.data.database.entity.MeasureEntity
import es.upm.bienestaremocional.data.questionnaire.ScoredManager
import es.upm.bienestaremocional.domain.repository.questionnaire.QuestionnaireRepository

abstract class ScoredNumericQuestionnaireViewModel(
    repository: QuestionnaireRepository<MeasureEntity>,
    manager: ScoredManager<MeasureEntity>,
    entityId: Long,
) : ScoredQuestionnaireViewModel(
    repository = repository,
    manager = manager,
    entityId = entityId
) {

    private val defaultAnswerValue = 0

    /**
     * Optional function to put a default not null value in numeric answers
     */
    fun onLoadQuestion(question: Int) {
        manager.apply {
            if (getAnswer(question) == null)
                setAnswer(question, defaultAnswerValue)
        }
    }

}