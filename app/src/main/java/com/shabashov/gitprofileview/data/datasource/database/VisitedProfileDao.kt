package com.shabashov.gitprofileview.data.datasource.database

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query

@Dao
interface VisitedProfileDao {
    @Query("SELECT * FROM visited_profiles")
    suspend fun getAll(): List<ProfileEntity>

    @Insert
    suspend fun addVisitedProfile(vararg profile: ProfileEntity)

    @Delete
    suspend fun delete(profileEntity: ProfileEntity)
}
