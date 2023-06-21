package es.upm.bienestaremocional.ui.screens.questionnaire

sealed class QuestionnaireState {
    object InProgress : QuestionnaireState()
    object SkipAttempt : QuestionnaireState()
    object Skipped : QuestionnaireState()
    object FinishAttempt : QuestionnaireState()
    object Summary : QuestionnaireState()
    object Finished : QuestionnaireState()
}