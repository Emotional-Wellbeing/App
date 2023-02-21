package es.upm.bienestaremocional.app.ui.state

sealed class QuestionnaireState {
    object InProgress : QuestionnaireState()
    object SkipAttempt: QuestionnaireState()
    object Skipped: QuestionnaireState()
    object FinishAttempt: QuestionnaireState()
    object Summary: QuestionnaireState()
    object Finished: QuestionnaireState()
}