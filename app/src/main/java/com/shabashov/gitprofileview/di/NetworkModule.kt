package com.shabashov.gitprofileview.di

import com.shabashov.gitprofileview.data.datasource.network.GithubApiService
import com.shabashov.gitprofileview.data.datasource.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideGithubApiService(): GithubApiService {
        return RetrofitClient.retrofit.create(GithubApiService::class.java)
    }

}