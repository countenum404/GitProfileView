package com.shabashov.gitprofileview.data.datasource.database

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.util.UUID

@Entity(tableName = "visited_profiles")
data class ProfileEntity(
    @PrimaryKey val uuid: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "followers_number") val followersNumber: Int,
    @ColumnInfo(name = "following_number") val followingNumber: Int,
    @ColumnInfo(name = "repo_number")  val publicRepositories: Int
)
