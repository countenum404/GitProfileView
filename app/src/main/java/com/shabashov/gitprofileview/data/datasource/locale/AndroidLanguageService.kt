package com.shabashov.gitprofileview.data.datasource.locale

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.shabashov.gitprofileview.domain.LanguageService
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import javax.inject.Inject

class AndroidLanguageService @Inject constructor(): LanguageService {
    override fun applyLanguage(language: AppLanguage) {
        val isoCode = language.isoCode
        val appLocale = LocaleListCompat.forLanguageTags(isoCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }
}