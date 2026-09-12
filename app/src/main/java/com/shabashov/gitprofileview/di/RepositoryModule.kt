package com.shabashov.gitprofileview.di

import android.content.Context
import androidx.room3.Room
import com.shabashov.gitprofileview.data.datasource.database.AppDatabase
import com.shabashov.gitprofileview.data.datasource.network.GithubApiService
import com.shabashov.gitprofileview.data.datasource.network.RetrofitClient
import com.shabashov.gitprofileview.data.repository.GithubRepository
import com.shabashov.gitprofileview.data.repository.VisitedProfilesRepositoryImpl
import com.shabashov.gitprofileview.domain.ProfileRepository
import com.shabashov.gitprofileview.domain.VisitedProfilesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsProfileRepository(
        githubRepository: GithubRepository
    ): ProfileRepository

    @Binds
    fun bindsViewedProfilesRepository(
        profileHistoryRepositoryImpl: VisitedProfilesRepositoryImpl
    ): VisitedProfilesRepository

    companion object {

        @Provides
        fun provideGithubApiService(): GithubApiService {
            return RetrofitClient.retrofit.create(GithubApiService::class.java)
        }

        @Provides
        fun providesVisitedProfilesRepositoryImpl(appDatabase: AppDatabase): VisitedProfilesRepositoryImpl {
            val profilesDao = appDatabase.visitedProfilesDao()
            return VisitedProfilesRepositoryImpl(profilesDao)
        }

        @Provides
        fun providesDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = "git-profile-view-database"
        ).build()

    }
}