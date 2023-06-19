package es.upm.bienestaremocional.data.remote.questionnaire.oneoff

import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress

data class OneOffQuestionnairesRequest(
    val userId: String,
    val data: Data
) {
    data class Data(
        val stress: List<OneOffStress>?,
        val depression: List<OneOffDepression>?,
        val loneliness: List<OneOffLoneliness>?,
    )

}