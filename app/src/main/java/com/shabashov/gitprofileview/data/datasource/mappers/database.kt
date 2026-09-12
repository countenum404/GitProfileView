package com.shabashov.gitprofileview.data.datasource.mappers

import com.shabashov.gitprofileview.data.datasource.database.ProfileEntity
import com.shabashov.gitprofileview.domain.Profile
import java.util.UUID


fun ProfileEntity.toProfile(): Profile = Profile(
    name = name,
    fullName = fullName,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    publicRepositories = publicRepositories
)

fun List<ProfileEntity>.toProfiles(): List<Profile> = map { it.toProfile() }

fun Profile.toProfileEntity(): ProfileEntity = ProfileEntity(
    uuid = UUID.randomUUID(),
    name = name,
    fullName = fullName,
    followersNumber = followersNumber,
    followingNumber = followingNumber,
    publicRepositories = publicRepositories
)
