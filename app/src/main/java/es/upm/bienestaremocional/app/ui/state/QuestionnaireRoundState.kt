package es.upm.bienestaremocional.app.ui.state

sealed class QuestionnaireRoundState {
    object PreShow: QuestionnaireRoundState()
    object Show: QuestionnaireRoundState()
    object Finishing: QuestionnaireRoundState()
    object Finished: QuestionnaireRoundState()
}