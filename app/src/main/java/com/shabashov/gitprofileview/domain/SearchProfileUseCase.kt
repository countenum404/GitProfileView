package com.shabashov.gitprofileview.domain

import android.util.Log
import com.shabashov.gitprofileview.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.HttpException

class SearchProfileUseCase(
    private val repository: ProfileRepository = GithubRepository()
) {
    operator fun invoke(name: String): Flow<List<Profile>> {
        Log.d("UseCase", "Use SearchProfileUseCase called")
        if (name.isEmpty() || name.isBlank()) {
            return flowOf()
        }
        return try {
            repository.searchByName(name)
        } catch (e: HttpException) {
            Log.d("SearchProfileUseCase", e.response().toString())
            flow { emit(emptyList()) }
        }
    }
}