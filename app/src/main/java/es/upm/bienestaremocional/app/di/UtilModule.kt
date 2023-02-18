package es.upm.bienestaremocional.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.upm.bienestaremocional.app.data.credits.Credit
import es.upm.bienestaremocional.app.data.credits.CreditContent

@Module
@InstallIn(ViewModelComponent::class)
object UtilModule
{
    @Provides
    @ViewModelScoped
    fun provideCredits(): List<Credit> = CreditContent.content
}