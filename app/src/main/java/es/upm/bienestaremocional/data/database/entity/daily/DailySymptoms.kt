package es.upm.bienestaremocional.data.database.entity.daily

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.upm.bienestaremocional.data.database.entity.MeasureEntity

/**
 * Mapping of DailySymptoms table
 */
@Entity(tableName = "daily_symptoms")
data class DailySymptoms(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "daily_symptoms_id")
    override val id: Long = 0,

    @ColumnInfo(name = "daily_symptoms_created_at")
    override val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_symptoms_modified_at")
    override var modifiedAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "daily_symptoms_completed")
    override var completed: Boolean = false,

    @ColumnInfo(name = "daily_symptoms_appetite")
    var appetite : Appetite? = null,
    @ColumnInfo(name = "daily_symptoms_energy")
    var energy : Energy? = null,
    @ColumnInfo(name = "daily_symptoms_rest")
    var rest : Rest? = null,
    @ColumnInfo(name = "daily_symptoms_focus")
    var focus : Focus? = null,
    @ColumnInfo(name = "daily_symptoms_libido")
    var libido : Libido? = null,
    @ColumnInfo(name = "daily_symptoms_pain")
    var pain : Pain? = null,
): MeasureEntity
{
    enum class Appetite {
        ExcessivelyHigh,
        Appropriate,
        ExcessivelyLow;

        companion object
        {
            val values = Appetite.values()
            fun get(ordinal: Int) : Appetite? = values.getOrNull(ordinal)
        }
    }
    enum class Energy {
        High,
        Moderate,
        Low;

        companion object
        {
            val values = Energy.values()
            fun get(ordinal: Int) : Energy? = values.getOrNull(ordinal)
        }
    }
    enum class Rest {
        Satisfactory,
        Moderate,
        Poor;

        companion object
        {
            val values = Rest.values()
            fun get(ordinal: Int) : Rest? = values.getOrNull(ordinal)
        }
    }
    enum class Focus {
        Satisfactory,
        Moderate,
        Poor;

        companion object
        {
            val values = Focus.values()
            fun get(ordinal: Int) : Focus? = values.getOrNull(ordinal)
        }
    }
    enum class Libido {
        Satisfactory,
        Moderate,
        Low;

        companion object
        {
            val values = Libido.values()
            fun get(ordinal: Int) : Libido? = values.getOrNull(ordinal)
        }
    }
    enum class Pain {
        NoPain,
        ModeratePain,
        HighPain;

        companion object
        {
            val values = Pain.values()
            fun get(ordinal: Int) : Pain? = values.getOrNull(ordinal)
        }
    }
}