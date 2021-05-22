package com.svenjacobs.app.leon.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val KEY_VERSION_CODE = intPreferencesKey("version_code")
private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun setVersionCode(versionCode: Int) {
        context.dataStore.edit {
            it[KEY_VERSION_CODE] = versionCode
        }
    }

    val versionCode: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[KEY_VERSION_CODE] ?: 0
        }
}