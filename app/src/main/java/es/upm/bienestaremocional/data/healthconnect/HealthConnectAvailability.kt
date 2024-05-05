package es.upm.bienestaremocional.data.healthconnect

import android.content.Context
import androidx.health.connect.client.HealthConnectClient

/**
 * Health Connect requires that the underlying Healthcore APK is installed on the device.
 * [HealthConnectAvailability] represents whether this APK is indeed installed, whether it is not
 * installed but supported on the device, or whether the device is not supported (based on Android
 * version).
 */

enum class HealthConnectAvailability {
    INSTALLED,
    NOT_INSTALLED,
    NOT_SUPPORTED;

    companion object {
        fun getAvailability(context: Context): HealthConnectAvailability {
            return when (HealthConnectClient.getSdkStatus(context)) {
                HealthConnectClient.SDK_UNAVAILABLE -> NOT_SUPPORTED
                HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> NOT_INSTALLED
                HealthConnectClient.SDK_AVAILABLE -> INSTALLED
                else -> NOT_SUPPORTED
            }
        }
    }
}