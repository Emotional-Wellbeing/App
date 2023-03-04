package es.upm.bienestaremocional.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.upm.bienestaremocional.app.data.database.dao.AppDAO
import es.upm.bienestaremocional.app.domain.repository.*
import es.upm.bienestaremocional.app.domain.repository.questionnaire.*
import javax.inject.Named

/**
 * Contains repositories related to questionnaires injected by Hilt
 */
@Module
@InstallIn(ViewModelComponent::class)
object QuestionnaireRepositoriesModule
{
    @Provides
    @ViewModelScoped
    fun providePHQRepository(dao: AppDAO, @Named("logTag") logTag: String): PHQRepository =
        PHQRepositoryImpl(dao,logTag)

    @Provides
    @ViewModelScoped
    fun providePSSRepository(dao: AppDAO, @Named("logTag") logTag: String): PSSRepository =
        PSSRepositoryImpl(dao,logTag)

    @Provides
    @ViewModelScoped
    fun provideQuestionnaireRoundRepository(dao: AppDAO, @Named("logTag") logTag: String):
            QuestionnaireRoundRepository = QuestionnaireRoundRepositoryImpl(dao,logTag)

    @Provides
    @ViewModelScoped
    fun provideQuestionnaireRoundFullRepository(dao: AppDAO, @Named("logTag") logTag: String):
            QuestionnaireRoundFullRepository = QuestionnaireRoundFullRepositoryImpl(dao,logTag)

    @Provides
    @ViewModelScoped
    fun provideUCLARepository(dao: AppDAO, @Named("logTag") logTag: String): UCLARepository =
        UCLARepositoryImpl(dao,logTag)
}
