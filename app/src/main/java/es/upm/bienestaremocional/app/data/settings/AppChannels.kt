package es.upm.bienestaremocional.app.data.settings

import android.app.NotificationManager
import es.upm.bienestaremocional.R

enum class AppChannels(val nameId: Int, val channelId: String, val importance: Int)
{
    Main(R.string.main_channel,"0", NotificationManager.IMPORTANCE_DEFAULT),
    Questionnaire(R.string.questionnaire_channel,"1", NotificationManager.IMPORTANCE_HIGH)
}