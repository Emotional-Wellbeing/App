package es.upm.bienestaremocional.di

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yariksoffice.lingver.Lingver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.upm.bienestaremocional.data.RemoteConstants
import es.upm.bienestaremocional.data.healthconnect.HealthConnectAvailability
import es.upm.bienestaremocional.data.info.AppInfo
import es.upm.bienestaremocional.data.info.AppInfoImpl
import es.upm.bienestaremocional.data.language.LanguageManager
import es.upm.bienestaremocional.data.language.LanguageManagerImpl
import es.upm.bienestaremocional.data.remote.RemoteAPI
import es.upm.bienestaremocional.data.settings.AppSettings
import es.upm.bienestaremocional.data.settings.AppSettingsImpl
import es.upm.bienestaremocional.data.worker.WorkAdministrator
import es.upm.bienestaremocional.data.worker.WorkAdministratorImpl
import es.upm.bienestaremocional.ui.notification.Notification
import es.upm.bienestaremocional.ui.notification.NotificationImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * Contains services injected by Hilt common to the entire application
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideHealthConnectClient(@ApplicationContext context: Context): HealthConnectClient =
        HealthConnectClient.getOrCreate(context)

    @Provides
    @Singleton
    fun provideHealthConnectAvailability(@ApplicationContext context: Context): HealthConnectAvailability =
        HealthConnectAvailability.getAvailability(context)


    @Provides
    @Singleton
    fun provideAppSettings(@ApplicationContext context: Context): AppSettings =
        AppSettingsImpl(context)

    @Provides
    @Singleton
    fun provideAppInfo(@ApplicationContext context: Context): AppInfo =
        AppInfoImpl(context)

    @Provides
    @Singleton
    fun provideNotification(
        @ApplicationContext context: Context,
        @Named("logTag") logTag: String
    ): Notification =
        NotificationImpl(context, logTag)

    @Provides
    @Singleton
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @Provides
    @Singleton
    fun provideWorkAdministrator(
        @ApplicationContext context: Context,
        @Named("logTag") logTag: String
    ): WorkAdministrator =
        WorkAdministratorImpl(context, logTag)

    @Provides
    @Singleton
    @Named("logTag")
    fun provideLogTag(): String = "BienestarEmocionalApp"


    @Provides
    @Singleton
    fun provideLanguageManager(application: Application): LanguageManager =
        LanguageManagerImpl(Lingver.init(application))

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .serializeNulls()
        .create()

    @Provides
    @Singleton
    fun provideRemoteAPI(gson: Gson): RemoteAPI = Retrofit.Builder()
        .baseUrl(RemoteConstants.SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(RemoteAPI::class.java)
}