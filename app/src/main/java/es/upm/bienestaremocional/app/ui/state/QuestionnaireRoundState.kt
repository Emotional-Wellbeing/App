package es.upm.bienestaremocional.app.ui.state

sealed class QuestionnaireRoundState {

    object Init: QuestionnaireRoundState()
    object Show: QuestionnaireRoundState()
    object PostShow: QuestionnaireRoundState() //state to avoid re-launch show
    object Finishing: QuestionnaireRoundState()
    object Finished: QuestionnaireRoundState()
}