package es.upm.bienestaremocional.data.database.entity.oneoff

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.ScoredEntity

/**
 * Mapping of One Off Stress table
 */
@Entity(tableName = "one_off_stress")
data class OneOffStress(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "one_off_stress_id")
    override val id: Long = 0,

    @ColumnInfo(name = "one_off_stress_created_at")
    override val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "one_off_stress_modified_at")
    override var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "one_off_stress_score")
    override var score: Int? = null,

    @ColumnInfo(name = "one_off_stress_score_level")
    override var scoreLevel: String? = null,

    @ColumnInfo(name = "one_off_stress_completed")
    override var completed: Boolean = false,

    @ColumnInfo(name = "one_off_stress_answer_1")
    var answer1: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_2")
    var answer2: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_3")
    var answer3: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_4")
    var answer4: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_5")
    var answer5: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_6")
    var answer6: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_7")
    var answer7: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_8")
    var answer8: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_9")
    var answer9: Int? = null,
    @ColumnInfo(name = "one_off_stress_answer_10")
    var answer10: Int? = null,
) : ScoredEntity
