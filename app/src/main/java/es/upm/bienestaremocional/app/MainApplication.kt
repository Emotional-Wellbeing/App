package es.upm.bienestaremocional.app

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * This class is needed to instantiate Hilt.
 */
@HiltAndroidApp
class MainApplication: Application(), Configuration.Provider
{
    // Work Manager with Hilt
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}