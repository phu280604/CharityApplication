package com.developing.charityapplication.data.dataManager

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreManager {

    private const val PREF_NAME = "app_prefs"
    private val Context.dataStore by preferencesDataStore(name = PREF_NAME)

    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val PROFILE_ID_KEY = stringPreferencesKey("profile_id")

    // SAVE
    suspend fun saveUserId(context: Context, userId: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
        }
    }

    suspend fun saveProfileId(context: Context, profileId: String) {
        context.dataStore.edit { prefs ->
            prefs[PROFILE_ID_KEY] = profileId
        }
    }

    // READ
    fun getUserId(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[USER_ID_KEY]
        }
    }

    fun getProfileId(context: Context): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[PROFILE_ID_KEY]
        }
    }

    // CLEAR ALL
    suspend fun clearAll(context: Context) {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
