package es.upm.bienestaremocional.app.ui.viewmodel

sealed class QuestionnaireState {
    object InProgress : QuestionnaireState()
    data class Summary(val score: Int): QuestionnaireState()
    object Skip: QuestionnaireState()
    object Finish: QuestionnaireState()
}