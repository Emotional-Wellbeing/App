package es.upm.bienestaremocional.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.questionnaire.daily.DailyDepressionManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyLonelinessManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailyStressManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailySuicideManager
import es.upm.bienestaremocional.data.questionnaire.daily.DailySymptomsManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffDepressionManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffLonelinessManager
import es.upm.bienestaremocional.data.questionnaire.oneoff.OneOffStressManager

/**
 * Contains repositories related to questionnaires injected by Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
object ManagersModule
{
    @Provides
    fun provideDailyStressManager(): DailyStressManager = DailyStressManager()

    @Provides
    fun provideDailyDepressionManager(): DailyDepressionManager = DailyDepressionManager()

    @Provides
    fun provideDailyLonelinessManager(): DailyLonelinessManager = DailyLonelinessManager()

    @Provides
    fun provideDailySuicideManager(): DailySuicideManager = DailySuicideManager()

    @Provides
    fun provideDailySymptomsManager(): DailySymptomsManager = DailySymptomsManager()

    @Provides
    fun provideOneOffStressManager(): OneOffStressManager = OneOffStressManager()

    @Provides
    fun provideOneOffDepressionManager(): OneOffDepressionManager = OneOffDepressionManager()

    @Provides
    fun provideOneOffLonelinessManager(): OneOffLonelinessManager = OneOffLonelinessManager()
}
