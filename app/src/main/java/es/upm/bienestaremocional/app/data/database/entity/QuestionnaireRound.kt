package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "questionnaire_round",
        foreignKeys = [
            ForeignKey(entity = PSS::class,
                childColumns = ["pss_id"],
                parentColumns = ["pss_id"]), //entity PSS
            ForeignKey(entity = PHQ::class,
                childColumns = ["phq_id"],
                parentColumns = ["phq_id"]), //entity PHQ
            ForeignKey(entity = UCLA::class,
                childColumns = ["ucla_id"],
                parentColumns = ["ucla_id"]), //entity UCLA
        ])
data class QuestionnaireRound(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "modified_at")
    var modifiedAt: Long = System.currentTimeMillis(),

    //mandatory questionnaire
    @ColumnInfo(name = "pss_id", index = true)
    val pss: Long,

    @ColumnInfo(name = "phq_id", index = true)
    var phq: Long? = null,

    @ColumnInfo(name = "ucla_id", index = true)
    var ucla: Long? = null,
)
