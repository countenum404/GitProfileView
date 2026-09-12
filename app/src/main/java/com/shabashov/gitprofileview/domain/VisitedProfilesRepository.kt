package com.shabashov.gitprofileview.domain

import kotlinx.coroutines.flow.Flow

interface VisitedProfilesRepository {
    suspend fun getAllProfiles(): Flow<List<Profile>>
    suspend fun saveProfileToRepository(profile: Profile)
}