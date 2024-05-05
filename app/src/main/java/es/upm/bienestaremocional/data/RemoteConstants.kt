package es.upm.bienestaremocional.data

import androidx.work.BackoffPolicy
import androidx.work.WorkRequest

object RemoteConstants {
    const val SERVER_URL = "http://nispero.etsisi.upm.es:5000"
    val BACKOFF_CRITERIA = BackoffPolicy.EXPONENTIAL
    const val BACKOFF_INITIAL_DELAY = WorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS
}