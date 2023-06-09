package es.upm.bienestaremocional.data.database.entity.daily

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.MeasureEntity

/**
 * Mapping of DailySuicide table
 */
@Entity(tableName = "daily_suicide")
data class DailySuicide(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_suicide_id")
    override val id: Long = 0,

    @ColumnInfo(name = "daily_suicide_created_at")
    override val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_suicide_modified_at")
    override var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_suicide_completed")
    override var completed: Boolean = false,

    @ColumnInfo(name = "daily_suicide_answer_1")
    var answer1 : Int? = null,
    @ColumnInfo(name = "daily_suicide_answer_2")
    var answer2 : Int? = null,
    @ColumnInfo(name = "daily_suicide_answer_3")
    var answer3 : Int? = null,
): MeasureEntity