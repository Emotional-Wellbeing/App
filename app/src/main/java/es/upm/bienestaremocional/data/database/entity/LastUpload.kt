package es.upm.bienestaremocional.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "last_upload")
data class LastUpload(
    @PrimaryKey
    val type : Type,
    var timestamp: Long //in seconds
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
        DailyStress,
        DailyDepression,
        DailyLoneliness,
        DailySuicide,
        DailySymptoms,
        OneOffStress,
        OneOffDepression,
        OneOffLoneliness
    }
}

