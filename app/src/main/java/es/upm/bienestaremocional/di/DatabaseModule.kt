package es.upm.bienestaremocional.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.database.AppDatabase
import es.upm.bienestaremocional.data.database.dao.AppDAO
import javax.inject.Singleton

/**
 * Contains services injected by Hilt used for interact with database
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideAppDao(appDatabase: AppDatabase): AppDAO = appDatabase.appDao()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "app_database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
}