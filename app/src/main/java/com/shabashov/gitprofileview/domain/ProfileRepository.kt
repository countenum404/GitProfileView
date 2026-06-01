package com.shabashov.gitprofileview.domain

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun searchByName(name: String): Flow<List<Profile>>
}