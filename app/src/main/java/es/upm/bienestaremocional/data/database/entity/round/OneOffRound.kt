package es.upm.bienestaremocional.data.database.entity.round

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import kotlinx.parcelize.Parcelize

/**
 * Mapping of Questionnaire Round table
 * @see OneOffStress
 * @see OneOffDepression
 * @see OneOffLoneliness
 */
@Parcelize
@Entity(tableName = "one_off_round",
        foreignKeys = [
            ForeignKey(entity = OneOffStress::class,
                childColumns = ["stress_id"],
                parentColumns = ["one_off_stress_id"]), //entity PSS
            ForeignKey(entity = OneOffDepression::class,
                childColumns = ["depression_id"],
                parentColumns = ["one_off_depression_id"]), //entity PHQ
            ForeignKey(entity = OneOffLoneliness::class,
                childColumns = ["loneliness_id"],
                parentColumns = ["one_off_loneliness_id"]), //entity UCLA
        ])
data class OneOffRound(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "modified_at")
    var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "stress_id", index = true)
    val stressId: Long? = null,

    @ColumnInfo(name = "depression_id", index = true)
    var depressionId: Long? = null,

    @ColumnInfo(name = "loneliness_id", index = true)
    var lonelinessId: Long? = null,
) : Parcelable
