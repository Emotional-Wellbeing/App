package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pss")
data class PSS(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pss_id")
    val id: Long = 0,

    @ColumnInfo(name = "pss_created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "pss_modified_at")
    var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "pss_score")
    var score: Int? = null,

    @ColumnInfo(name = "pss_score_level")
    var scoreLevel: String? = null,

    @ColumnInfo(name = "pss_completed")
    var completed: Boolean = false,

    @ColumnInfo(name = "pss_answer_1")
    var answer1 : Int? = null,
    @ColumnInfo(name = "pss_answer_2")
    var answer2 : Int? = null,
    @ColumnInfo(name = "pss_answer_3")
    var answer3 : Int? = null,
    @ColumnInfo(name = "pss_answer_4")
    var answer4 : Int? = null,
    @ColumnInfo(name = "pss_answer_5")
    var answer5 : Int? = null,
    @ColumnInfo(name = "pss_answer_6")
    var answer6 : Int? = null,
    @ColumnInfo(name = "pss_answer_7")
    var answer7 : Int? = null,
    @ColumnInfo(name = "pss_answer_8")
    var answer8 : Int? = null,
    @ColumnInfo(name = "pss_answer_9")
    var answer9 : Int? = null,
    @ColumnInfo(name = "pss_answer_10")
    var answer10 : Int? = null,
)
