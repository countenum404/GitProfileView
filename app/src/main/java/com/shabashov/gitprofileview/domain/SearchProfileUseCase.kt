package com.shabashov.gitprofileview.domain

import android.util.Log
import com.shabashov.gitprofileview.data.TestRepository
import kotlinx.coroutines.flow.Flow

class SearchProfileUseCase(
    private val repository: ProfileRepository = TestRepository
) {
    operator fun invoke(name: String): Flow<List<Profile>> {
        Log.d("UseCase", "Use SearchProfileUseCase called")
        return repository.searchByName(name)
    }
}