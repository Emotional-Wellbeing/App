package es.upm.bienestaremocional.ui.screens.debug

sealed class DebugState {
    object ShowOptions : DebugState()
    object QueryAllQuestionnaireRounds: DebugState()
    object QueryUncompletedQuestionnaireRounds: DebugState()
    object QueryWorkManager: DebugState()
    object GetCommunity: DebugState()
}
