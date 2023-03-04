package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Mapping of UCLA table
 */
@Entity(tableName = "ucla")
data class UCLA(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ucla_id")
    val id: Long = 0,

    @ColumnInfo(name = "ucla_created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "ucla_modified_at")
    var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "ucla_score")
    var score: Int? = null,

    @ColumnInfo(name = "ucla_score_level")
    var scoreLevel: String? = null,

    @ColumnInfo(name = "ucla_completed")
    var completed: Boolean = false,

    @ColumnInfo(name = "ucla_answer_1")
    var answer1 : Int? = null,
    @ColumnInfo(name = "ucla_answer_2")
    var answer2 : Int? = null,
    @ColumnInfo(name = "ucla_answer_3")
    var answer3 : Int? = null,
    @ColumnInfo(name = "ucla_answer_4")
    var answer4 : Int? = null,
    @ColumnInfo(name = "ucla_answer_5")
    var answer5 : Int? = null,
    @ColumnInfo(name = "ucla_answer_6")
    var answer6 : Int? = null,
    @ColumnInfo(name = "ucla_answer_7")
    var answer7 : Int? = null,
    @ColumnInfo(name = "ucla_answer_8")
    var answer8 : Int? = null,
    @ColumnInfo(name = "ucla_answer_9")
    var answer9 : Int? = null,
    @ColumnInfo(name = "ucla_answer_10")
    var answer10 : Int? = null,
    @ColumnInfo(name = "ucla_answer_11")
    var answer11 : Int? = null,
    @ColumnInfo(name = "ucla_answer_12")
    var answer12 : Int? = null,
    @ColumnInfo(name = "ucla_answer_13")
    var answer13 : Int? = null,
    @ColumnInfo(name = "ucla_answer_14")
    var answer14 : Int? = null,
    @ColumnInfo(name = "ucla_answer_15")
    var answer15 : Int? = null,
    @ColumnInfo(name = "ucla_answer_16")
    var answer16 : Int? = null,
    @ColumnInfo(name = "ucla_answer_17")
    var answer17 : Int? = null,
    @ColumnInfo(name = "ucla_answer_18")
    var answer18 : Int? = null,
    @ColumnInfo(name = "ucla_answer_19")
    var answer19 : Int? = null,
    @ColumnInfo(name = "ucla_answer_20")
    var answer20 : Int? = null,
)
