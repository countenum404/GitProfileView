package com.shabashov.gitprofileview.domain.interfaces.settings

import com.shabashov.gitprofileview.domain.entity.AppLanguage

interface LanguageService {
    fun applyLanguage(language: AppLanguage)
}
