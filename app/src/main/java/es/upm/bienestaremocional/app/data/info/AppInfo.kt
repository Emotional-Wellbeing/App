package es.upm.bienestaremocional.app.data.info

import kotlinx.coroutines.flow.Flow

/**
 * Contains the operations related with app info as app is executed for first time, user id
 * @see AppInfoImpl
 */
interface AppInfo
{
    /**
     * Save first time info
     * @param value: Preference to save
     */
    suspend fun saveFirstTime(value: Boolean)

    /**
     * Get if the app is executed for first time
     * @return [Flow] of [Boolean] with the values
     */
    suspend fun getFirstTime(): Flow<Boolean>

    /**
     * Get User ID
     * @return [String] with the value
     */
    suspend fun getUserID(): String
}