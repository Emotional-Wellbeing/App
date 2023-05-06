package es.upm.bienestaremocional.app.di

import androidx.health.connect.client.HealthConnectClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.upm.bienestaremocional.app.data.healthconnect.sources.*
import es.upm.bienestaremocional.core.extraction.healthconnect.data.HealthConnectManagerInterface

/**
 * Contains Health Connect sources injected by Hilt
 */
@Module
@InstallIn(ViewModelComponent::class)
object HealthConnectSourcesModule
{
    
    @Provides
    @ViewModelScoped
    fun provideDistance(healthConnectClient: HealthConnectClient,
                        healthConnectManager: HealthConnectManagerInterface
    ): Distance = Distance(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideElevationGained(healthConnectClient: HealthConnectClient,
                               healthConnectManager: HealthConnectManagerInterface
    ): ElevationGained = ElevationGained(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideHeartRate(healthConnectClient: HealthConnectClient,
                         healthConnectManager: HealthConnectManagerInterface
    ): HeartRate = HeartRate(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideSleep(healthConnectClient: HealthConnectClient,
                     healthConnectManager: HealthConnectManagerInterface
    ): Sleep = Sleep(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideSteps(healthConnectClient: HealthConnectClient,
                     healthConnectManager: HealthConnectManagerInterface
    ): Steps = Steps(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideTotalCaloriesBurned(healthConnectClient: HealthConnectClient,
                                   healthConnectManager: HealthConnectManagerInterface
    ): TotalCaloriesBurned = TotalCaloriesBurned(healthConnectClient, healthConnectManager)

    @Provides
    @ViewModelScoped
    fun provideWeight(healthConnectClient: HealthConnectClient,
                     healthConnectManager: HealthConnectManagerInterface
    ): Weight = Weight(healthConnectClient, healthConnectManager)
}