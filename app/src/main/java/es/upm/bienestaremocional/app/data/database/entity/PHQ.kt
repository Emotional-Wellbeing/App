package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Mapping of PHQ table
 */
@Entity(tableName = "phq")
data class PHQ(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "phq_id")
    val id: Long = 0,

    @ColumnInfo(name = "phq_created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "phq_modified_at")
    var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "phq_score")
    var score: Int? = null,

    @ColumnInfo(name = "phq_score_level")
    var scoreLevel: String? = null,

    @ColumnInfo(name = "phq_completed")
    var completed: Boolean = false,

    @ColumnInfo(name = "phq_answer_1")
    var answer1 : Int? = null,
    @ColumnInfo(name = "phq_answer_2")
    var answer2 : Int? = null,
    @ColumnInfo(name = "phq_answer_3")
    var answer3 : Int? = null,
    @ColumnInfo(name = "phq_answer_4")
    var answer4 : Int? = null,
    @ColumnInfo(name = "phq_answer_5")
    var answer5 : Int? = null,
    @ColumnInfo(name = "phq_answer_6")
    var answer6 : Int? = null,
    @ColumnInfo(name = "phq_answer_7")
    var answer7 : Int? = null,
    @ColumnInfo(name = "phq_answer_8")
    var answer8 : Int? = null,
    @ColumnInfo(name = "phq_answer_9")
    var answer9 : Int? = null
)
