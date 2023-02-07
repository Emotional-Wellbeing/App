package es.upm.bienestaremocional.app.ui.state

sealed class QuestionnaireState {
    object InProgress : QuestionnaireState()
    object Skipping: QuestionnaireState()
    object Skipped: QuestionnaireState()
    object FinishingQuestions: QuestionnaireState()
    object Summary: QuestionnaireState()
    object Finished: QuestionnaireState()
}