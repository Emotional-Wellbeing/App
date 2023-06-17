package es.upm.bienestaremocional.data.database.entity.daily

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.ScoredEntity

/**
 * Mapping of DailyLoneliness table
 */
@Entity(tableName = "daily_loneliness")
data class DailyLoneliness(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_loneliness_id")
    override val id: Long = 0,

    @ColumnInfo(name = "daily_loneliness_created_at")
    override val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_loneliness_modified_at")
    override var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_loneliness_score")
    override var score: Int? = null,

    @ColumnInfo(name = "daily_loneliness_score_level")
    override var scoreLevel: String? = null,

    @ColumnInfo(name = "daily_loneliness_completed")
    override var completed: Boolean = false,

    @ColumnInfo(name = "daily_loneliness_answer_1")
    var answer1 : Int? = null,
    @ColumnInfo(name = "daily_loneliness_answer_2")
    var answer2 : Int? = null,
    @ColumnInfo(name = "daily_loneliness_answer_3")
    var answer3 : Int? = null,
    @ColumnInfo(name = "daily_loneliness_answer_4")
    var answer4 : Int? = null,
): ScoredEntity