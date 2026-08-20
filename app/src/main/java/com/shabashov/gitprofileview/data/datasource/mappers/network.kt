package com.shabashov.gitprofileview.data.datasource.mappers

import com.shabashov.gitprofileview.data.datasource.network.SearchUserItem
import com.shabashov.gitprofileview.data.datasource.network.User
import com.shabashov.gitprofileview.domain.Profile


fun List<User>.toProfileList(): List<Profile> {
    return map { user ->
        Profile(
            name = user.login,
            fullName = user.nodeId,
            followersNumber = 0,
            followingNumber = 0,
            publicRepositories = 0
        )
    }.toList()
}
