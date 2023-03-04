package es.upm.bienestaremocional.app.data.language

import android.content.Context
import com.yariksoffice.lingver.Lingver
import java.util.*

/**
 * Contains the operations related to language
 */
class LanguageManagerImpl(private val lingver: Lingver): LanguageManager
{
    private val supportedLocale : List<Locale> = listOf(Locale("es"), Locale("en"))

    override fun getSupportedLocalesLabel() : List<String> =
        supportedLocale.map { it.displayLanguage.capitalized() }

    override fun getLocale(): Int =
        supportedLocale.indexOf(lingver.getLocale())

    override fun changeLocale(context: Context, index: Int): Unit? =
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