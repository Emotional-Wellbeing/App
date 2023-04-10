package es.upm.bienestaremocional.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.app.data.healthconnect.sources.Sleep
import es.upm.bienestaremocional.app.data.remote.RemoteAPI
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.app.domain.repository.remote.RemoteRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteRepositoriesModule
{
    @Provides
    @Singleton
    fun provideRemoteRepository(remoteAPI: RemoteAPI, sleep: Sleep): RemoteRepository =
        RemoteRepositoryImpl(remoteAPI, sleep)
}