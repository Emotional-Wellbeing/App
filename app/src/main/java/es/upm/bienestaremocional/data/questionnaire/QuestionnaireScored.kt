package es.upm.bienestaremocional.data.questionnaire

/**
 * Contain the essential data of all questionnaires that have score
 */
interface QuestionnaireScored {
    val levels: List<ScoreLevel>
    val minScore: Int
    val maxScore: Int
}