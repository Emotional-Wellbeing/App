package es.upm.bienestaremocional.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.app.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.app.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.app.data.healthconnect.sources.ExerciseSession
import es.upm.bienestaremocional.app.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.app.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.app.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.app.data.healthconnect.sources.Weight
import es.upm.bienestaremocional.app.data.info.AppInfo
import es.upm.bienestaremocional.app.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.app.domain.usecases.PostUserDataUseCase
import es.upm.bienestaremocional.app.domain.usecases.PostUserDataUseCaseImpl
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule
{
    @Provides
    fun providePostUserDataUseCase(
        @Named("logTag") logTag: String,
        appInfo : AppInfo,
        remoteRepository : RemoteRepository,
        lastUploadRepository : LastUploadRepository,
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
            weight = weight)
}