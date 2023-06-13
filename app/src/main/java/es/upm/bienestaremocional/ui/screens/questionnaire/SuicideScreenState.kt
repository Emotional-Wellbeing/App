package es.upm.bienestaremocional.ui.screens.questionnaire

sealed class SuicideScreenState
{
    object InProgress : SuicideScreenState()
    object SkipAttempt: SuicideScreenState()
    object Skipped: SuicideScreenState()
    object Summary: SuicideScreenState()
    object Finished: SuicideScreenState()
}