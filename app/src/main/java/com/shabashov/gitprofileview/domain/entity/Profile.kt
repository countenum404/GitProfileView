package com.shabashov.gitprofileview.domain.entity

data class Profile(
    val name: String,
    val fullName: String,
    val followersNumber: Int,
    val followingNumber: Int,
    val publicRepositories: Int
)