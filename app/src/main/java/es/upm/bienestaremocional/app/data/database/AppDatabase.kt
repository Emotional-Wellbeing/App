package es.upm.bienestaremocional.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.data.database.entity.PHQ
import es.upm.bienestaremocional.app.data.database.entity.PSS
import es.upm.bienestaremocional.app.data.database.entity.QuestionnaireRound
import es.upm.bienestaremocional.app.data.database.entity.UCLA

@Database(entities = [QuestionnaireRound::class, PHQ::class, PSS::class, UCLA::class],
version = 1,
exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun appDao(): AppDAO
}