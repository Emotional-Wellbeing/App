package es.upm.bienestaremocional.data.database.entity.round

import androidx.room.Embedded
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress

/**
 * Mapping of Questionnaire Round with the associated entities.
 * @see OneOffRound
 * @see OneOffStress
 * @see OneOffDepression
 * @see OneOffLoneliness
 */
data class OneOffRoundFull(
    @Embedded
    val oneOffRound: OneOffRound,

    @Embedded
    val oneOffStress: OneOffStress,

    @Embedded
    val oneOffDepression: OneOffDepression?,

    @Embedded
    val oneOffLoneliness: OneOffLoneliness?
)
