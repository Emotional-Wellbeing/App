package es.upm.bienestaremocional.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import es.upm.bienestaremocional.core.ui.responsive.WindowSize


//https://guides.codepath.com/android/Understanding-the-Android-Application-Class

/**
 * This class contains default objects that need context to be instanced.
 * They are usually used in viewmodels
 */
@HiltAndroidApp
class MainApplication: Application()
{
    companion object
    {
        //init at MainActivity
        var windowSize : WindowSize? = null
    }
}