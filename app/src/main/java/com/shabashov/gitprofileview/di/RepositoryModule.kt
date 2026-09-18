package com.shabashov.gitprofileview.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room3.Room
import com.shabashov.gitprofileview.data.datasource.database.AppDatabase
import com.shabashov.gitprofileview.data.datasource.locale.AndroidLanguageService
import com.shabashov.gitprofileview.data.datasource.network.GithubApiService
import com.shabashov.gitprofileview.data.datasource.network.RetrofitClient
import com.shabashov.gitprofileview.data.repository.GithubRepository
import com.shabashov.gitprofileview.data.repository.SettingsRepositoryImpl
import com.shabashov.gitprofileview.data.repository.VisitedProfilesRepositoryImpl
import com.shabashov.gitprofileview.domain.interfaces.settings.LanguageService
import com.shabashov.gitprofileview.domain.interfaces.profile.ProfileRepository
import com.shabashov.gitprofileview.domain.interfaces.settings.SettingsRepository
import com.shabashov.gitprofileview.domain.interfaces.settings.VisitedProfilesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Binds
    fun bindsSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository

    @Binds
    fun bindsLanguageService(
        androidLanguageService: AndroidLanguageService
    ): LanguageService

    companion object {

        @Provides
        @Singleton
        fun provideGithubApiService(): GithubApiService {
            return RetrofitClient.retrofit.create(GithubApiService::class.java)
        }

        @Provides
        @Singleton
        fun providesVisitedProfilesRepositoryImpl(appDatabase: AppDatabase): VisitedProfilesRepositoryImpl {
            val profilesDao = appDatabase.visitedProfilesDao()
            return VisitedProfilesRepositoryImpl(profilesDao)
        }

        @Provides
        @Singleton
        fun providesDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
            context = context.applicationContext,
            klass = AppDatabase::class.java,
            name = "git-profile-view-database"
        ).build()

        @Provides
        @Singleton
        fun providesDataStore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile("user_settings_git_profile_view")
            }
        }
    }
}