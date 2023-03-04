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
    fun provideActiveCaloriesBurned(healthConnectClient: HealthConnectClient, 
                                    healthConnectManager: HealthConnectManagerInterface
    ): ActiveCaloriesBurned = ActiveCaloriesBurned(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideBasalMetabolicRate(healthConnectClient: HealthConnectClient,
                                  healthConnectManager: HealthConnectManagerInterface
    ): BasalMetabolicRate = BasalMetabolicRate(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideBloodGlucose(healthConnectClient: HealthConnectClient,
                            healthConnectManager: HealthConnectManagerInterface
    ): BloodGlucose = BloodGlucose(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideBloodPressure(healthConnectClient: HealthConnectClient,
                             healthConnectManager: HealthConnectManagerInterface
    ): BloodPressure = BloodPressure(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideBodyTemperature(healthConnectClient: HealthConnectClient,
                               healthConnectManager: HealthConnectManagerInterface
    ): BodyTemperature = BodyTemperature(healthConnectClient, healthConnectManager)
    
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
    fun provideOxygenSaturation(healthConnectClient: HealthConnectClient,
                                healthConnectManager: HealthConnectManagerInterface
    ): OxygenSaturation = OxygenSaturation(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideRespiratoryRate(healthConnectClient: HealthConnectClient,
                               healthConnectManager: HealthConnectManagerInterface
    ): RespiratoryRate = RespiratoryRate(healthConnectClient, healthConnectManager)
    
    @Provides
    @ViewModelScoped
    fun provideRestingHeartRate(healthConnectClient: HealthConnectClient,
                                healthConnectManager: HealthConnectManagerInterface
    ): RestingHeartRate = RestingHeartRate(healthConnectClient, healthConnectManager)
    
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
    fun proviceVo2Max(healthConnectClient: HealthConnectClient,
                      healthConnectManager: HealthConnectManagerInterface
    ): Vo2Max = Vo2Max(healthConnectClient, healthConnectManager)
}