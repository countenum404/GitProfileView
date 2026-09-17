package com.shabashov.gitprofileview.domain.usecase.profile

import com.shabashov.gitprofileview.domain.entity.Profile
import com.shabashov.gitprofileview.domain.interfaces.settings.VisitedProfilesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllViewedProfilesUseCase @Inject constructor(private val repository: VisitedProfilesRepository) {
    suspend operator fun invoke(): Flow<List<Profile>> {
        return repository.getAllProfiles()
    }
}