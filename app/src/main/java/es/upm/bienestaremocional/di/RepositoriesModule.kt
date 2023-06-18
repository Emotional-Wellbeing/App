package es.upm.bienestaremocional.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.database.dao.AppDAO
import es.upm.bienestaremocional.data.remote.RemoteAPI
import es.upm.bienestaremocional.domain.repository.LastUploadRepository
import es.upm.bienestaremocional.domain.repository.LastUploadRepositoryImpl
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepository
import es.upm.bienestaremocional.domain.repository.remote.RemoteRepositoryImpl
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {
    @Provides
    fun provideLastUploadRepository(
        dao: AppDAO,
        @Named("logTag") logTag: String
    ): LastUploadRepository =
        LastUploadRepositoryImpl(dao, logTag)

    @Provides
    fun provideRemoteRepository(
        @Named("logTag") logTag: String,
        remoteAPI: RemoteAPI
    ): RemoteRepository =
        RemoteRepositoryImpl(
            logTag = logTag,
            remoteAPI = remoteAPI
        )
}