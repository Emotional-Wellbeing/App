package es.upm.bienestaremocional.data.database.entity.daily

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.ScoredEntity

/**
 * Mapping of DailyDepression table
 */
@Entity(tableName = "daily_depression")
data class DailyDepression(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_depression_id")
    override val id: Long = 0,

    @ColumnInfo(name = "daily_depression_created_at")
    override val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_depression_modified_at")
    override var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_depression_score")
    override var score: Int? = null,

    @ColumnInfo(name = "daily_depression_score_level")
    override var scoreLevel: String? = null,

    @ColumnInfo(name = "daily_depression_completed")
    override var completed: Boolean = false,

    @ColumnInfo(name = "daily_depression_answer_1")
    var answer1: Int? = null,
    @ColumnInfo(name = "daily_depression_answer_2")
    var answer2: Int? = null,
    @ColumnInfo(name = "daily_depression_answer_3")
    var answer3: Int? = null,
) : ScoredEntity