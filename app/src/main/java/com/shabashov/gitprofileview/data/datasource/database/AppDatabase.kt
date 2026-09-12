package com.shabashov.gitprofileview.data.datasource.database

import androidx.room3.Database
import androidx.room3.RoomDatabase

@Database(entities = [ProfileEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun visitedProfilesDao(): VisitedProfileDao
}
