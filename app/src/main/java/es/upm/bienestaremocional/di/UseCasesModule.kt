package es.upm.bienestaremocional.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.data.healthconnect.sources.ExerciseSession
import es.upm.bienestaremocional.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.data.healthconnect.sources.Weight
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailyStressRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySuicideRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.DailySymptomsRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffDepressionRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffLonelinessRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffRoundRepository
import es.upm.bienestaremocional.domain.repository.questionnaire.OneOffStressRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.domain.usecases.InsertDailyRoundUseCase
import es.upm.bienestaremocional.domain.usecases.InsertDailyRoundUseCaseImpl
import es.upm.bienestaremocional.domain.usecases.InsertOneOffRoundUseCase
import es.upm.bienestaremocional.domain.usecases.InsertOneOffRoundUseCaseImpl
import es.upm.bienestaremocional.domain.usecases.PostDailyQuestionnairesUseCase
import es.upm.bienestaremocional.domain.usecases.PostDailyQuestionnairesUseCaseImpl
import es.upm.bienestaremocional.domain.usecases.PostOneOffQuestionnairesUseCase
import es.upm.bienestaremocional.domain.usecases.PostOneOffQuestionnairesUseCaseImpl
import es.upm.bienestaremocional.domain.usecases.PostUserDataUseCase
import es.upm.bienestaremocional.domain.usecases.PostUserDataUseCaseImpl
import javax.inject.Named

/**
 * Use cases are used in multiple parts on the app and one of these uses is by Worker objects.
 * Worker objects can use only SingletonComponent dependencies and their transitive dependencies
 * also need to be SingletonComponent
 */

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {
    @Provides
    fun providePostUserDataUseCase(
        @Named("logTag") logTag: String,
        appInfo: AppInfo,
        remoteRepository: RemoteRepository,
        lastUploadRepository: LastUploadRepository,
        distance: Distance,
        elevationGained: ElevationGained,
        exerciseSession: ExerciseSession,
        floorsClimbed: FloorsClimbed,
        heartRate: HeartRate,
        sleep: Sleep,
        steps: Steps,
        totalCaloriesBurned: TotalCaloriesBurned,
        weight: Weight
    ): PostUserDataUseCase =
        PostUserDataUseCaseImpl(
            logTag = logTag,
            appInfo = appInfo,
            remoteRepository = remoteRepository,
            lastUploadRepository = lastUploadRepository,
            distance = distance,
            elevationGained = elevationGained,
            exerciseSession = exerciseSession,
            floorsClimbed = floorsClimbed,
            heartRate = heartRate,
            sleep = sleep,
            steps = steps,
            totalCaloriesBurned = totalCaloriesBurned,
            weight = weight
        )

    @Provides
    fun provideInsertDailyRoundUseCase(
        @Named("logTag") logTag: String,
        appSettings: AppSettings,
        dailyStressRepository: DailyStressRepository,
        dailyDepressionRepository: DailyDepressionRepository,
        dailyLonelinessRepository: DailyLonelinessRepository,
        dailySuicideRepository: DailySuicideRepository,
        dailySymptomsRepository: DailySymptomsRepository,
        dailyRoundRepository: DailyRoundRepository
    ): InsertDailyRoundUseCase = InsertDailyRoundUseCaseImpl(
        logTag = logTag,
        appSettings = appSettings,
        dailyStressRepository = dailyStressRepository,
        dailyDepressionRepository = dailyDepressionRepository,
        dailyLonelinessRepository = dailyLonelinessRepository,
        dailySuicideRepository = dailySuicideRepository,
        dailySymptomsRepository = dailySymptomsRepository,
        dailyRoundRepository = dailyRoundRepository
    )

    @Provides
    fun provideInsertOneOffRoundUseCase(
        @Named("logTag") logTag: String,
        appSettings: AppSettings,
        oneOffStressRepository: OneOffStressRepository,
        oneOffDepressionRepository: OneOffDepressionRepository,
        oneOffLonelinessRepository: OneOffLonelinessRepository,
        oneOffRoundRepository: OneOffRoundRepository
    ): InsertOneOffRoundUseCase = InsertOneOffRoundUseCaseImpl(
        logTag = logTag,
        appSettings = appSettings,
        oneOffStressRepository = oneOffStressRepository,
        oneOffDepressionRepository = oneOffDepressionRepository,
        oneOffLonelinessRepository = oneOffLonelinessRepository,
        oneOffRoundRepository = oneOffRoundRepository
    )

    @Provides
    fun providePostDailyQuestionnairesUseCase(
        @Named("logTag") logTag: String,
        appInfo: AppInfo,
        remoteRepository: RemoteRepository,
        lastUploadRepository: LastUploadRepository,
        dailyStressRepository: DailyStressRepository,
        dailyDepressionRepository: DailyDepressionRepository,
        dailyLonelinessRepository: DailyLonelinessRepository,
        dailySuicideRepository: DailySuicideRepository,
        dailySymptomsRepository: DailySymptomsRepository,
    ): PostDailyQuestionnairesUseCase = PostDailyQuestionnairesUseCaseImpl(
        logTag = logTag,
        appInfo = appInfo,
        remoteRepository = remoteRepository,
        lastUploadRepository = lastUploadRepository,
        dailyStressRepository = dailyStressRepository,
        dailyDepressionRepository = dailyDepressionRepository,
        dailyLonelinessRepository = dailyLonelinessRepository,
        dailySuicideRepository = dailySuicideRepository,
        dailySymptomsRepository = dailySymptomsRepository
    )

    @Provides
    fun providePostOneOffQuestionnairesUseCase(
        @Named("logTag") logTag: String,
        appInfo: AppInfo,
        remoteRepository: RemoteRepository,
        lastUploadRepository: LastUploadRepository,
        oneOffStressRepository: OneOffStressRepository,
        oneOffDepressionRepository: OneOffDepressionRepository,
        oneOffLonelinessRepository: OneOffLonelinessRepository,
    ): PostOneOffQuestionnairesUseCase = PostOneOffQuestionnairesUseCaseImpl(
        logTag = logTag,
        appInfo = appInfo,
        remoteRepository = remoteRepository,
        lastUploadRepository = lastUploadRepository,
        oneOffStressRepository = oneOffStressRepository,
        oneOffDepressionRepository = oneOffDepressionRepository,
        oneOffLonelinessRepository = oneOffLonelinessRepository,
    )
}