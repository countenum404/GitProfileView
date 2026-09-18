package com.shabashov.gitprofileview.domain.interfaces.settings

import com.shabashov.gitprofileview.domain.entity.AppLanguage
import com.shabashov.gitprofileview.domain.entity.Settings
import com.shabashov.gitprofileview.domain.entity.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val languageFlow: Flow<AppLanguage>
    suspend fun setLanguage(language: AppLanguage)

    val themeFlow: Flow<Theme>
    suspend fun setTheme(theme: Theme)

    val settingsFlow: Flow<Settings>
}
