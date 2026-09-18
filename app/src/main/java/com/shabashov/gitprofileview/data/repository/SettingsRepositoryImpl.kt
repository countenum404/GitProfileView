package com.shabashov.gitprofileview.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.shabashov.gitprofileview.domain.interfaces.settings.SettingsRepository
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import com.shabashov.gitprofileview.domain.entity.Settings
import com.shabashov.gitprofileview.domain.entity.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object PreferencesKeys {
        val LANGUAGE = stringPreferencesKey("app_language")
        val THEME = stringPreferencesKey("app_theme")
    }

    override val languageFlow: Flow<AppLanguage>
        get() = dataStore.data.map { preferences ->
            AppLanguage.valueOf(preferences[PreferencesKeys.LANGUAGE] ?: AppLanguage.EN.name)
        }

    override suspend fun setLanguage(language: AppLanguage) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language.name
        }
    }

    override val themeFlow: Flow<Theme>
        get() = dataStore.data.map {preferences ->
            Theme.valueOf(preferences[PreferencesKeys.THEME] ?: Theme.WHITE.name)
        }

    override suspend fun setTheme(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = theme.name
        }
    }

    override val settingsFlow: Flow<Settings>
        get() = dataStore.data.map { preferences ->
            Settings(
                language = AppLanguage.valueOf(
                    preferences[PreferencesKeys.LANGUAGE] ?: AppLanguage.EN.name
                ),
                theme = Theme.valueOf(
                    preferences[PreferencesKeys.THEME] ?: Theme.WHITE.name
                )
            )
        }


}