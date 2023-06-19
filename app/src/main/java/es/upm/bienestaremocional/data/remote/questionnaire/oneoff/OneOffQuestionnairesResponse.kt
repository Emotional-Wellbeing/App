package es.upm.bienestaremocional.data.remote.questionnaire.oneoff

data class OneOffQuestionnairesResponse(
    val code: Int,
    val timestamps: Timestamps?
) {
    data class Timestamps(
        val stress: Long?,
        val depression: Long?,
        val loneliness: Long?,
    )
}
