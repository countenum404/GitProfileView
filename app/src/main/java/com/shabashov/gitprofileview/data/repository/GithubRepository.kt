package com.shabashov.gitprofileview.data.repository

import android.util.Log
import com.shabashov.gitprofileview.data.datasource.network.GithubApiService
import com.shabashov.gitprofileview.data.datasource.network.RetrofitClient
import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.domain.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

//   countenum404

class GithubRepository(
    val apiService: GithubApiService = RetrofitClient.retrofit.create(GithubApiService::class.java)
): ProfileRepository {
    override fun searchByName(name: String): Flow<List<Profile>> {
        return flow {
            apiService.findByLogin(name).let { response ->
                Log.d("GithubRepository", "Got response from github: $response")
                if (response.totalCount > 0) {
                    response.items.take(3).map { searchUserItem ->
                        try {
                            Log.d("GithubRepository", "Trying to find user: ${searchUserItem.login}")
                            val response = apiService.getUserByLogin(searchUserItem.login)
                            Log.d("GithubRepository", "Response: $response")
                            val user = response.body()!!
                            Profile(
                                name = user.login,
                                fullName = user.name ?: "No name",
                                followersNumber = user.followers,
                                followingNumber = user.following,
                                publicRepositories = user.publicRepos
                            )
                        } catch (e: Exception) {
                            Log.e("GithubRepository", "Got response from github: $e")

                            Profile(
                                name = "",
                                fullName = "",
                                followersNumber = 0,
                                followingNumber = 0,
                                publicRepositories = 0
                            )
                        }
                    }.toList().let { emit(it) }
                } else {
                    emit(emptyList())
                }
            }
        }
    }
}