package com.shabashov.gitprofileview.domain.entity

enum class AppLanguage(val isoCode: String) { EN("en"), RU("ru") }

data class Settings(
    val language: AppLanguage
)
