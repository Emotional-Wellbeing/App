package es.upm.bienestaremocional.app.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_upload")
data class LastUpload(
    @PrimaryKey
    val type : Type,
    var timestamp: Long
)
{
    enum class Type {
        Distance,
        ElevationGained,
        ExerciseSession,
        FloorsClimbed,
        HeartRate,
        Sleep,
        Steps,
        TotalCaloriesBurned,
        Weight,
    }
}

