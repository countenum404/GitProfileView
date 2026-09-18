package com.shabashov.gitprofileview.domain.usecase.profile

import android.util.Log
import com.shabashov.gitprofileview.domain.entity.Profile
import com.shabashov.gitprofileview.domain.interfaces.profile.ProfileRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(name: String): Flow<List<Profile>> {
        Log.d("UseCase", "Use SearchProfileUseCase called")
        if (name.isEmpty() || name.isBlank()) {
            return flowOf()
        }
        return repository.searchByName(name)
    }
}