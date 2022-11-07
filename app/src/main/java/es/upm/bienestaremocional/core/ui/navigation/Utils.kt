package es.upm.bienestaremocional.core.ui.navigation

import android.content.Context
import android.content.Intent

fun openForeignActivity(context: Context, action: String)
{
    val settingsIntent = Intent()
    settingsIntent.action = action
    context.startActivity(settingsIntent)
}