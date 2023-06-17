package es.upm.bienestaremocional.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.database.entity.LastUpload
import es.upm.bienestaremocional.data.database.entity.daily.DailyDepression
import es.upm.bienestaremocional.data.database.entity.daily.DailyLoneliness
import es.upm.bienestaremocional.data.database.entity.daily.DailyStress
import es.upm.bienestaremocional.data.database.entity.daily.DailySuicide
import es.upm.bienestaremocional.data.database.entity.daily.DailySymptoms
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffDepression
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffLoneliness
import es.upm.bienestaremocional.data.database.entity.oneoff.OneOffStress
import es.upm.bienestaremocional.data.database.entity.round.DailyRound
import es.upm.bienestaremocional.data.database.entity.round.OneOffRound

/**
 * Instance of app database
 */
@Database(
    entities = [
        LastUpload::class,
        OneOffRound::class,
        DailyRound::class,
        OneOffDepression::class,
        OneOffStress::class,
        OneOffLoneliness::class,
        DailyStress::class,
        DailyDepression::class,
        DailyLoneliness::class,
        DailySuicide::class,
        DailySymptoms::class,
   ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun appDao(): AppDAO
}