package es.upm.bienestaremocional.app.data.database.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionnaireRoundReduced(
    val qrId: Long = 0,
    val pssId: Long? = null,
    val phqId: Long? = null,
    val uclaId: Long? = null,
): Parcelable