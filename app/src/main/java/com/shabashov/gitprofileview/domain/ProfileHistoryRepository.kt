package com.shabashov.gitprofileview.domain

import kotlinx.coroutines.flow.Flow

interface ProfileHistoryRepository {
    suspend fun getAllProfiles(): Flow<List<Profile>>
    suspend fun saveProfileToRepository(profile: Profile)
}