package com.shabashov.gitprofileview.domain.usecase.settings

import android.util.Log
import com.shabashov.gitprofileview.domain.LanguageService
import com.shabashov.gitprofileview.domain.SettingsRepository
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ChangeLanguageUseCase @Inject constructor(
    val settingsRepository: SettingsRepository,
    val languageService: LanguageService
) {
    suspend operator fun invoke(language: AppLanguage) {
        val currentLanguage = settingsRepository.languageFlow.first()
        if (language != currentLanguage) {
            settingsRepository.setLanguage(language)
            languageService.applyLanguage(language)
        }
    }
}