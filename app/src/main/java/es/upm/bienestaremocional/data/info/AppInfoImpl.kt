package es.upm.bienestaremocional.data.info

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import es.upm.bienestaremocional.data.generateUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Implementation of [AppInfo] using DataStore
 */
class AppInfoImpl(private val context: Context) : AppInfo {
    companion object {
        /**
         * DataStore object used for ACID operations
         */
        private val Context.appInfoDataStore: DataStore<Preferences>
                by preferencesDataStore(name = "info")

        //preferences keys of the settings
        private val FIRST_TIME = booleanPreferencesKey("first_time")
        private val USER_ID = stringPreferencesKey("user_id")

        //defaults values
        private const val FIRST_TIME_DEFAULT_VALUE = true
    }

    override suspend fun saveFirstTime(value: Boolean) {
        context.appInfoDataStore.edit { preferences ->
            preferences[FIRST_TIME] = value
        }
    }

    override suspend fun getFirstTime(): Flow<Boolean> =
        context.appInfoDataStore.data.map { preferences ->
            preferences[FIRST_TIME] ?: FIRST_TIME_DEFAULT_VALUE
        }


    private suspend fun setUserID(uid: String) {
        context.appInfoDataStore.edit { preferences ->
            preferences[USER_ID] = uid
        }
    }

    override suspend fun getUserID(): String {
        val uid = context.appInfoDataStore.data.first()[USER_ID]

        return if (uid == null) {
            val newUid = generateUID()
            setUserID(newUid)
            newUid
        }
        else
            uid
    }
}