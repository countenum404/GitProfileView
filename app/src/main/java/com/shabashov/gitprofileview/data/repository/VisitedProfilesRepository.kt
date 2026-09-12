package com.shabashov.gitprofileview.data.repository

import com.shabashov.gitprofileview.data.datasource.database.VisitedProfileDao
import com.shabashov.gitprofileview.data.datasource.mappers.toProfileEntity
import com.shabashov.gitprofileview.data.datasource.mappers.toProfiles
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.domain.VisitedProfilesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class VisitedProfilesRepositoryImpl @Inject constructor(
    private val profilesDao: VisitedProfileDao
): VisitedProfilesRepository {
    override suspend fun getAllProfiles(): Flow<List<Profile>> = flowOf(
        profilesDao.getAll().toProfiles()
    )

    override suspend fun saveProfileToRepository(profile: Profile) {
        profilesDao.addVisitedProfile(profile.toProfileEntity())
    }
}