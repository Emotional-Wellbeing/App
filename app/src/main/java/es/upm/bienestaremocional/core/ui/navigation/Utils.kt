package es.upm.bienestaremocional.core.ui.navigation

import android.content.Context
import android.content.Intent

/**
 * Open activity from other app
 */
fun openForeignActivity(context: Context, action: String) = context.startActivity(Intent(action))