package es.upm.bienestaremocional.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


/**
 * This class is needed to instantiate Hilt.
 */
@HiltAndroidApp
class MainApplication: Application()