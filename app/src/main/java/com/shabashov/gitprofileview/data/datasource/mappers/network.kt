package com.shabashov.gitprofileview.data.datasource.mappers

import com.shabashov.gitprofileview.data.datasource.network.User
import com.shabashov.gitprofileview.domain.Profile


fun User.toProfile(): Profile = Profile(
    name = this.login,
    fullName = this.name ?: "No name",
    followersNumber = this.followers,
    followingNumber = this.following,
    publicRepositories = this.publicRepos
)

fun emptyProfile(): Profile = Profile(
    name = "",
    fullName = "",
    followersNumber = 0,
    followingNumber = 0,
    publicRepositories = 0
)
