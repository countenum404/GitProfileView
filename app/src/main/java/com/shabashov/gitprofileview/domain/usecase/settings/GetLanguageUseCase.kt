package com.shabashov.gitprofileview.domain.usecase.settings

import com.shabashov.gitprofileview.domain.SettingsRepository
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(): Flow<AppLanguage> {
        return settingsRepository.languageFlow
    }
}