package es.upm.bienestaremocional.app.ui.state

sealed class DebugState {
    object ShowOptions : DebugState()
    object QueryAllQuestionnaireRounds: DebugState()
    object QueryUncompletedQuestionnaireRounds: DebugState()
}
