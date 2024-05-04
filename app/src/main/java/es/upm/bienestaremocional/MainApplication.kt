package es.upm.bienestaremocional

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


/**
 * This class is needed to instantiate Hilt.
 */
@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider {
    // Work Manager with Hilt
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    /**
     * The [Configuration] used to initialize WorkManager
     */
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}