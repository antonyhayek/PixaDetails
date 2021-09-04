package com.antonyhayek.pixadetails.data.local.prefsstore

import kotlinx.coroutines.flow.Flow
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PrefsStore(
    private val dataStore: DataStore<Preferences>
) {

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            // Get our show completed value, defaulting to false if not set:
            val loggedIn = preferences[PreferencesKeys.IS_LOGGED_IN]?: false
            UserPreferences(loggedIn)
        }

    suspend fun updateIsLoggedIn(loggedIn: Boolean) {

        dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_LOGGED_IN] = loggedIn
        }
    }

    private object PreferencesKeys {
        val IS_LOGGED_IN = booleanPreferencesKey("is_looged_in")
    }
}


