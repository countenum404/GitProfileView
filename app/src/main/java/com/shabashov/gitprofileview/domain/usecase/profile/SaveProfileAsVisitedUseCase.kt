package com.shabashov.gitprofileview.domain.usecase.profile

import com.shabashov.gitprofileview.domain.entity.Profile
import com.shabashov.gitprofileview.domain.VisitedProfilesRepository
import javax.inject.Inject

class SaveProfileAsVisitedUseCase @Inject constructor(private val repository: VisitedProfilesRepository) {
    suspend operator fun invoke(profile: Profile) {
        repository.saveProfileToRepository(profile)
    }
}
