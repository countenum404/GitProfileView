package com.shabashov.gitprofileview.domain.usecase.settings

import com.shabashov.gitprofileview.domain.interfaces.settings.SettingsRepository
import com.shabashov.gitprofileview.domain.entity.Settings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(): Flow<Settings> {
        return settingsRepository.settingsFlow
    }
}