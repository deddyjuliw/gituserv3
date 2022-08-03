package com.djw.mygithubuserapp.mode

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ModePreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey("theme_mode")

    fun getThemeMode(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeMode(isNightModeOn: Boolean) {
        dataStore.edit { preference ->
            preference[THEME_KEY] = isNightModeOn
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ModePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>) : ModePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ModePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}