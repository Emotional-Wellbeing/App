package es.upm.bienestaremocional.app.data.language

import android.content.Context

interface LanguageManager
{
    /**
     * Get the locales available on the application to show it
     * @return List with the Locales in String format
     */
    fun getSupportedLocalesLabel() : List<String>

    /**
     * Get actual locale
     * @return Actual locale in Int format
     */
    fun getLocale(): Int

    /**
     * Change the locale of the application using the locale's index
     */
    fun changeLocale(context: Context, index: Int) : Unit?
}