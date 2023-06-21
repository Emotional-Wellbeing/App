package es.upm.bienestaremocional.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundFullRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundFullRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySymptomsRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySymptomsRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffDepressionRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffLonelinessRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundFullRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundFullRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundRepositoryImpl
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffStressRepositoryImpl
import javax.inject.Named

/**
 * Contains repositories related to questionnaires injected by Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
object QuestionnaireRepositoriesModule {
    @Provides
    fun provideDailyStressRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailyStressRepository =
        DailyStressRepositoryImpl(dao, logTag)

    @Provides
    fun provideDailyDepressionRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailyDepressionRepository =
        DailyDepressionRepositoryImpl(dao, logTag)

    @Provides
    fun provideDailyLonelinessRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailyLonelinessRepository =
        DailyLonelinessRepositoryImpl(dao, logTag)

    @Provides
    fun provideDailySuicideRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailySuicideRepository =
        DailySuicideRepositoryImpl(dao, logTag)

    @Provides
    fun provideDailySymptomsRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailySymptomsRepository =
        DailySymptomsRepositoryImpl(dao, logTag)

    @Provides
    fun provideDailyRoundRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailyRoundRepository =
        DailyRoundRepositoryImpl(dao, logTag)

    @Provides
    fun provideDailyRoundFullRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): DailyRoundFullRepository =
        DailyRoundFullRepositoryImpl(dao, logTag)

    @Provides
    fun provideOneOffStressRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): OneOffStressRepository =
        OneOffStressRepositoryImpl(dao, logTag)

    @Provides
    fun provideOneOffDepressionRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): OneOffDepressionRepository =
        OneOffDepressionRepositoryImpl(dao, logTag)

    @Provides
    fun provideOneOffLonelinessRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): OneOffLonelinessRepository =
        OneOffLonelinessRepositoryImpl(dao, logTag)

    @Provides
    fun provideOneOffRoundFullRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): OneOffRoundFullRepository =
        OneOffRoundFullRepositoryImpl(dao, logTag)

    @Provides
    fun provideOneOffRoundRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): OneOffRoundRepository =
        OneOffRoundRepositoryImpl(dao, logTag)

}
