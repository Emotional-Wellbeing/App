package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Mapping of Questionnaire Round with the associated entities.
 * @see QuestionnaireRound
 * @see QuestionnaireRoundReduced
 * @see PSS
 * @see PHQ
 * @see UCLA
 */
data class QuestionnaireRoundFull(
    @Embedded
    val questionnaireRound: QuestionnaireRound,

    @Relation(parentColumn = "pss_id", entityColumn = "pss_id")
    val pss: PSS,

    @Relation(parentColumn = "phq_id", entityColumn = "phq_id")
    val phq: PHQ?,

    @Relation(parentColumn = "ucla_id", entityColumn = "ucla_id")
    val ucla: UCLA?
)
