package com.shabashov.gitprofileview.domain.interfaces.profile

import com.shabashov.gitprofileview.domain.entity.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun searchByName(name: String): Flow<List<Profile>>
}