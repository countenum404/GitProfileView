package com.shabashov.gitprofileview.data.repository

import android.util.Log
import com.shabashov.gitprofileview.data.datasource.mappers.emptyProfile
import com.shabashov.gitprofileview.data.datasource.mappers.toProfile
import com.shabashov.gitprofileview.data.datasource.network.GithubApiService
import com.shabashov.gitprofileview.data.datasource.network.RetrofitClient
import com.shabashov.gitprofileview.data.datasource.network.User
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.domain.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

//   countenum404

class GithubRepository(
    val apiService: GithubApiService = RetrofitClient.retrofit.create(GithubApiService::class.java)
): ProfileRepository {
    companion object {
        private const val TAG = "GithubRepository"
        private const val USERS_SIZE = 3
    }

    override fun searchByName(name: String): Flow<List<Profile>> {
        return flow {
            apiService.findByLogin(name).let { response ->
                Log.d("GithubRepository", "Got response from github: $response")

                if (response.totalCount == 0) {
                    emit(emptyList())
                }

                response.items.take(USERS_SIZE).map { searchUserItem ->
                    withContext(Dispatchers.IO) {
                        async {
                            apiService.getUserByLogin(searchUserItem.login).also {
                                Log.d(TAG, "Got for user ${searchUserItem.login} response $response")
                            }.body()?.toProfile() ?: emptyProfile()
                        }.await()
                    }
                }.toList().let { emit(it) }
            }
        }.catch { e ->
            Log.e(TAG, "Network error: ${e.message}", e)
            emit(emptyList())
        }
    }
}