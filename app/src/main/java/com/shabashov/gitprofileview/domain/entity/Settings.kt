package com.shabashov.gitprofileview.domain.entity

enum class AppLanguage(val isoCode: String) { EN("en"), RU("ru") }
enum class Theme { WHITE, DARK }

data class Settings(
    val language: AppLanguage,
    val theme: Theme
)
