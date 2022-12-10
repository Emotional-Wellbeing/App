package es.upm.bienestaremocional.app.data.settings

import android.content.Context
import com.yariksoffice.lingver.Lingver
import java.util.*

class LanguageManager(private val lingver: Lingver)
{
    private val supportedLocale : List<Locale> = listOf(Locale("es"), Locale("en"))

    fun getSupportedLocalesLabel() : List<String> =
        supportedLocale.map { it.displayLanguage.capitalized() }

    fun getLocale(): Int =
        supportedLocale.indexOf(lingver.getLocale())

    fun changeLocale(context: Context, index: Int) =
        supportedLocale.getOrNull(index)?.let {
            lingver.setLocale(context,it)
        }

    private fun String.capitalized(): String
    {
        return this.replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else it.toString()
        }
    }
}