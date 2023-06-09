package es.upm.bienestaremocional.di

import androidx.health.connect.client.HealthConnectClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.healthconnect.HealthConnectManager
import es.upm.bienestaremocional.data.healthconnect.sources.Distance
import es.upm.bienestaremocional.data.healthconnect.sources.ElevationGained
import es.upm.bienestaremocional.data.healthconnect.sources.ExerciseSession
import es.upm.bienestaremocional.data.healthconnect.sources.FloorsClimbed
import es.upm.bienestaremocional.data.healthconnect.sources.HeartRate
import es.upm.bienestaremocional.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.data.healthconnect.sources.Steps
import es.upm.bienestaremocional.data.healthconnect.sources.TotalCaloriesBurned
import es.upm.bienestaremocional.data.healthconnect.sources.Weight

/**
 * Contains Health Connect sources injected by Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
object HealthConnectSourcesModule
{
    
    @Provides
    fun provideDistance(healthConnectClient: HealthConnectClient,
                        healthConnectManager: HealthConnectManager
    ): Distance = Distance(healthConnectClient, healthConnectManager)
    
    @Provides
    fun provideElevationGained(healthConnectClient: HealthConnectClient,
                               healthConnectManager: HealthConnectManager
    ): ElevationGained = ElevationGained(healthConnectClient, healthConnectManager)

    @Provides
    fun provideExerciseSession(healthConnectClient: HealthConnectClient,
                               healthConnectManager: HealthConnectManager
    ): ExerciseSession = ExerciseSession(healthConnectClient, healthConnectManager)

    @Provides
    fun provideFloorsClimbed(healthConnectClient: HealthConnectClient,
                      healthConnectManager: HealthConnectManager
    ): FloorsClimbed = FloorsClimbed(healthConnectClient, healthConnectManager)
    
    @Provides
    fun provideHeartRate(healthConnectClient: HealthConnectClient,
                         healthConnectManager: HealthConnectManager
    ): HeartRate = HeartRate(healthConnectClient, healthConnectManager)
    
    @Provides
    fun provideSleep(healthConnectClient: HealthConnectClient,
                     healthConnectManager: HealthConnectManager
    ): Sleep = Sleep(healthConnectClient, healthConnectManager)
    
    @Provides
    fun provideSteps(healthConnectClient: HealthConnectClient,
                     healthConnectManager: HealthConnectManager
    ): Steps = Steps(healthConnectClient, healthConnectManager)
    
    @Provides
    fun provideTotalCaloriesBurned(healthConnectClient: HealthConnectClient,
                                   healthConnectManager: HealthConnectManager
    ): TotalCaloriesBurned = TotalCaloriesBurned(healthConnectClient, healthConnectManager)

    @Provides
    fun provideWeight(healthConnectClient: HealthConnectClient,
                     healthConnectManager: HealthConnectManager
    ): Weight = Weight(healthConnectClient, healthConnectManager)
}