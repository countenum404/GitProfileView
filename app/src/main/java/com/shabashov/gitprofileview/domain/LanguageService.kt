package com.shabashov.gitprofileview.domain

import com.shabashov.gitprofileview.domain.entity.AppLanguage

interface LanguageService {
    fun applyLanguage(language: AppLanguage)
}
