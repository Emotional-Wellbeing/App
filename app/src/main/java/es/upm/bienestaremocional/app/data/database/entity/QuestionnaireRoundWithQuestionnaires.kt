package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionnaireRoundWithQuestionnaires(
    @Embedded
    val questionnaireRound: QuestionnaireRound,

    @Relation(parentColumn = "pss_id", entityColumn = "pss_id")
    val pss: PSS,

    @Relation(parentColumn = "phq_id", entityColumn = "phq_id")
    val phq: PHQ?,

    @Relation(parentColumn = "ucla_id", entityColumn = "ucla_id")
    val ucla: UCLA?
)
