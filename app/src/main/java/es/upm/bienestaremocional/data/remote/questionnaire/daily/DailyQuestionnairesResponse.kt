package es.upm.bienestaremocional.data.remote.questionnaire.daily

data class DailyQuestionnairesResponse(
    val code: Int,
    val timestamps: Timestamps?
) {
    data class Timestamps(
        val stress: Long?,
        val depression: Long?,
        val loneliness: Long?,
        val suicide: Long?,
        val symptoms: Long?,
    )
}
