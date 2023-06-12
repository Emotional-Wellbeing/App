package es.upm.bienestaremocional.data.remote.userdata

import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms

data class DailyQuestionnairesRequest(
    val userId : String,
    val data : Data
)
{
    data class Data(
        val stress: List<DailyStress>?,
        val depression: List<DailyDepression>?,
        val loneliness: List<DailyLoneliness>?,
        val suicide: List<DailySuicide>?,
        val symptoms: List<DailySymptoms>?,
    )

}