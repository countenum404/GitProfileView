package com.shabashov.gitprofileview.domain

import javax.inject.Inject

class SaveProfileAsVisitedUseCase @Inject constructor(private val repository: VisitedProfilesRepository) {
    suspend operator fun invoke(profile: Profile) {
        repository.saveProfileToRepository(profile)
    }
}
