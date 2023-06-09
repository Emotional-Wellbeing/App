package es.upm.bienestaremocional.data.questionnaire

interface QuestionnaireScored {
    val levels: List<ScoreLevel>
    val minScore: Int
    val maxScore: Int
}