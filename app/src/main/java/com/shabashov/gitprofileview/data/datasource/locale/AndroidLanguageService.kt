package com.shabashov.gitprofileview.data.datasource.locale

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.shabashov.gitprofileview.domain.interfaces.settings.LanguageService
import com.shabashov.gitprofileview.domain.entity.AppLanguage
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject
import kotlin.jvm.java

class AndroidLanguageService @Inject constructor(
    @ApplicationContext private val context: Context
) : LanguageService {

    override fun applyLanguage(language: AppLanguage) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Log.d("AndroidLanguageService", "Applied more than tiramisu")
            applyForApi33Plus(language)
        } else {
            Log.d("AndroidLanguageService", "Applied less than tiramisu")
            applyForLegacy(language)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun applyForApi33Plus(language: AppLanguage) {
        val localeManager = context.getSystemService(LocaleManager::class.java)
            ?: return

        val locales = LocaleList(Locale.forLanguageTag(language.isoCode))
        localeManager.applicationLocales = locales
    }

    private fun applyForLegacy(language: AppLanguage) {
        val locales = LocaleListCompat.forLanguageTags(language.isoCode)
        AppCompatDelegate.setApplicationLocales(locales)
    }
}