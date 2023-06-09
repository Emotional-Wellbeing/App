package es.upm.bienestaremocional.data.database.entity.round

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Mapping of Questionnaire Round only with the id of associated entities.
 * @see DailyRound
 * @see DailyRoundFull
 */
@Parcelize
data class DailyRoundReduced(
    val qrId: Long = 0,
    val pssId: Long? = null,
    val phqId: Long? = null,
    val uclaId: Long? = null,
): Parcelable