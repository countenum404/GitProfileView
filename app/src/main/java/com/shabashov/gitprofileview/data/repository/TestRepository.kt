package com.shabashov.gitprofileview.data.repository

import com.shabashov.gitprofileview.domain.Profile
import com.shabashov.gitprofileview.domain.VisitedProfilesRepository
import com.shabashov.gitprofileview.domain.ProfileRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

object TestRepository : ProfileRepository {
    fun getTestData(): Profile {
        return Profile(
            name = "countenum404",
            fullName = "Denis Shabashov",
            followersNumber = 4,
            followingNumber = 11,
            publicRepositories = 35
        )
    }

    override fun searchByName(name: String): Flow<List<Profile>> {
        val profiles = listOf(getTestData())
        return MutableStateFlow(profiles.filter { it.name.contains(name) }).asStateFlow()
    }
}

object TestViewedProfilesRepository : VisitedProfilesRepository {

    private val savedProfiles = mutableListOf(
        Profile(
            name = "countenum404",
            fullName = "Denis Shabashov",
            followersNumber = 4,
            followingNumber = 11,
            publicRepositories = 35
        ),
        Profile(
            name = "test",
            fullName = "John Doe",
            followersNumber = 4,
            followingNumber = 11,
            publicRepositories = 35
        )
    )

    override suspend fun getAllProfiles(): Flow<List<Profile>> {
        delay(1500)
        throw Exception("test")
        return flowOf(savedProfiles)
    }

    override suspend fun saveProfileToRepository(profile: Profile) {
        savedProfiles.add(profile)
    }
}