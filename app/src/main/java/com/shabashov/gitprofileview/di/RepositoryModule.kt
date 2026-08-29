package com.shabashov.gitprofileview.di

import com.shabashov.gitprofileview.data.repository.GithubRepository
import com.shabashov.gitprofileview.domain.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesRepository(githubRepository: GithubRepository): ProfileRepository
}