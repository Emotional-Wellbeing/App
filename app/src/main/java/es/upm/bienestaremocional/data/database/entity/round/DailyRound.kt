package es.upm.bienestaremocional.data.database.entity.round

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import kotlinx.parcelize.Parcelize

/**
 * Mapping of Daily Round table
 * @see DailyStress
 * @see DailyDepression
 * @see DailyLoneliness
 * @see DailySuicide
 * @see DailySymptoms
 */
@Parcelize
@Entity(tableName = "daily_round",
        foreignKeys = [
            ForeignKey(entity = DailyStress::class,
                childColumns = ["stress_id"],
                parentColumns = ["daily_stress_id"]), //entity DailyStress
            ForeignKey(entity = DailyDepression::class,
                childColumns = ["depression_id"],
                parentColumns = ["daily_depression_id"]), //entity DailyDepression
            ForeignKey(entity = DailyLoneliness::class,
                childColumns = ["loneliness_id"],
                parentColumns = ["daily_loneliness_id"]), //entity DailyLoneliness
            ForeignKey(entity = DailySuicide::class,
                childColumns = ["suicide_id"],
                parentColumns = ["daily_suicide_id"]), //entity DailySuicide
            ForeignKey(entity = DailySymptoms::class,
                childColumns = ["symptoms_id"],
                parentColumns = ["daily_symptoms_id"]), //entity DailySymptoms
        ])
data class DailyRound(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "modified_at")
    var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "moment")
    val moment: Moment,

    @ColumnInfo(name = "stress_id", index = true)
    val stressId: Long? = null,

    @ColumnInfo(name = "depression_id", index = true)
    var depressionId: Long? = null,

    @ColumnInfo(name = "loneliness_id", index = true)
    var lonelinessId: Long? = null,

    @ColumnInfo(name = "suicide_id", index = true)
    var suicideId: Long? = null,

    @ColumnInfo(name = "symptoms_id", index = true)
    var symptomsId: Long? = null,
) : Parcelable
{
    enum class Moment
    {
        Morning,
        Night;
        companion object
        {
            private val values: Array<Moment> = values()

            operator fun get(ordinal: Int): Moment? {
                return values.getOrNull(ordinal)
            }
        }
    }
}
