package es.upm.bienestaremocional.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object
    {
        /*The value of a volatile variable will never be cached, and all writes and reads will be done to and from the main memory.
        This helps make sure the value of INSTANCE is always up-to-date and the same for all execution threads.
        It means that changes made by one thread to INSTANCE are visible to all other threads immediately.*/
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase
        {
            // only one thread of execution at a time can enter this block of code
            synchronized(this)
            {
                return INSTANCE ?: Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build().also {
                        INSTANCE = it
                    }
            }
        }
    }
}