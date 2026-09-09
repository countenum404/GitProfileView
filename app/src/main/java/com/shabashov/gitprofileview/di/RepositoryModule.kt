package com.shabashov.gitprofileview.di

import com.shabashov.gitprofileview.data.repository.GithubRepository
import com.shabashov.gitprofileview.data.repository.TestViewedProfilesRepository
import com.shabashov.gitprofileview.domain.ProfileHistoryRepository
import com.shabashov.gitprofileview.domain.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun providesProfileRepository(githubRepository: GithubRepository): ProfileRepository

    @Binds
    fun bindsViewedProfilesRepository(viewedProfilesRepository: TestViewedProfilesRepository): ProfileHistoryRepository

}