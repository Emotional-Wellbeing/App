package es.upm.bienestaremocional.data.healthconnect

/**
 * Health Connect requires that the underlying Healthcore APK is installed on the device.
 * [HealthConnectAvailability] represents whether this APK is indeed installed, whether it is not
 * installed but supported on the device, or whether the device is not supported (based on Android
 * version).
 * @see HealthConnectManager
 */

enum class HealthConnectAvailability {
    INSTALLED,
    NOT_INSTALLED,
    NOT_SUPPORTED
}