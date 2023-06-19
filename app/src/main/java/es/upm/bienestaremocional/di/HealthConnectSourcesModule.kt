package es.upm.bienestaremocional.di

import androidx.health.connect.client.HealthConnectClient
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
import javax.inject.Named

/**
 * Contains Health Connect sources injected by Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
object HealthConnectSourcesModule {

    @Provides
    fun provideDistance(
        healthConnectClient: HealthConnectClient
    ): Distance = Distance(healthConnectClient)

    @Provides
    fun provideElevationGained(
        healthConnectClient: HealthConnectClient
    ): ElevationGained = ElevationGained(healthConnectClient)

    @Provides
    fun provideExerciseSession(
        healthConnectClient: HealthConnectClient
    ): ExerciseSession = ExerciseSession(healthConnectClient)

    @Provides
    fun provideFloorsClimbed(
        healthConnectClient: HealthConnectClient
    ): FloorsClimbed = FloorsClimbed(healthConnectClient)

    @Provides
    fun provideHeartRate(
        healthConnectClient: HealthConnectClient
    ): HeartRate = HeartRate(healthConnectClient)

    @Provides
    fun provideSleep(
        healthConnectClient: HealthConnectClient,
        @Named("logTag") logTag: String
    ): Sleep = Sleep(healthConnectClient, logTag)

    @Provides
    fun provideSteps(
        healthConnectClient: HealthConnectClient
    ): Steps = Steps(healthConnectClient)

    @Provides
    fun provideTotalCaloriesBurned(
        healthConnectClient: HealthConnectClient
    ): TotalCaloriesBurned = TotalCaloriesBurned(healthConnectClient)

    @Provides
    fun provideWeight(
        healthConnectClient: HealthConnectClient
    ): Weight = Weight(healthConnectClient)
}