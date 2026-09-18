package com.shabashov.gitprofileview.domain.usecase.settings

import com.shabashov.gitprofileview.domain.interfaces.settings.SettingsRepository
import com.shabashov.gitprofileview.domain.entity.Theme
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ChangeThemeUseCase @Inject constructor(
    val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(theme: Theme) {
        val currentTheme = settingsRepository.themeFlow.first()
        if (theme != currentTheme) {
            settingsRepository.setTheme(theme)
        }
    }
}