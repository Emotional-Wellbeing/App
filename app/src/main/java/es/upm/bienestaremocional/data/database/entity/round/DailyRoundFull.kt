package es.upm.bienestaremocional.data.database.entity.round

import androidx.room.Embedded
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms

/**
 * Mapping of Questionnaire Round with the associated entities.
 * @see DailyRound
 * @see DailyStress
 * @see DailyDepression
 * @see DailyLoneliness
 * @see DailySuicide
 * @see DailySymptoms
 */
data class DailyRoundFull(
    @Embedded
    val dailyRound: DailyRound,

    @Embedded
    val dailyStress: DailyStress?,

    @Embedded
    val dailyDepression: DailyDepression?,

    @Embedded
    val dailyLoneliness: DailyLoneliness?,

    @Embedded
    val dailySuicide: DailySuicide?,

    @Embedded
    val dailySymptoms: DailySymptoms?
)