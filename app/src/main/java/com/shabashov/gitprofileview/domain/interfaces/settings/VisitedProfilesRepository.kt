package com.shabashov.gitprofileview.domain.interfaces.settings

import com.shabashov.gitprofileview.domain.entity.Profile
import kotlinx.coroutines.flow.Flow

interface VisitedProfilesRepository {
    suspend fun getAllProfiles(): Flow<List<Profile>>
    suspend fun saveProfileToRepository(profile: Profile)
}