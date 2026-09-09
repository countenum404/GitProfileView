package com.shabashov.gitprofileview.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllViewedProfilesUseCase @Inject constructor(private val repository: ProfileHistoryRepository) {
    suspend operator fun invoke(): Flow<List<Profile>> {
        return repository.getAllProfiles()
    }
}