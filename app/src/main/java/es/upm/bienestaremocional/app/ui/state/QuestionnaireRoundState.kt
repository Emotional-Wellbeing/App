package es.upm.bienestaremocional.app.ui.state

sealed class QuestionnaireRoundState {
    data class InProgress(val index: Int) : QuestionnaireRoundState()
    object Finishing: QuestionnaireRoundState()
    object Finished: QuestionnaireRoundState()
}