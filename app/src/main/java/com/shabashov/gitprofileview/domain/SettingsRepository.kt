package com.shabashov.gitprofileview.domain

import com.shabashov.gitprofileview.domain.entity.AppLanguage
import com.shabashov.gitprofileview.domain.entity.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    val languageFlow: Flow<AppLanguage>
    suspend fun setLanguage(language: AppLanguage)

}
