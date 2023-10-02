package com.example.githubsearch.di

import android.content.Context
import androidx.room.Room
import com.example.github_data.data.repository.GithubRepositoryImpl
import com.example.github_data.data.repository.local.LocalGithubUserDataSource
import com.example.github_data.data.repository.local.LocalGithubUserDataSourceImpl
import com.example.github_data.data.repository.remote.RemoteGithubUserDataSource
import com.example.github_data.data.repository.remote.RemoteGithubUserDataSourceImpl
import com.example.github_data.data.room.GithubUserDao
import com.example.github_data.data.room.GithubUserDatabase
import com.example.github_data.retrofit.GitHubService
import com.example.github_data.retrofit.GithubApi
import com.example.github_domain.Bookmark
import com.example.github_domain.GithubUserManager
import com.example.github_domain.SearchGithubUser
import com.example.github_domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    // 저장소
    @Binds
    abstract fun bindRemoteGithubUserDataSource(remoteGithubUserDataSource: RemoteGithubUserDataSourceImpl): RemoteGithubUserDataSource

    @Binds
    abstract fun bindGithubUserDatabase(localGithubUserDataSource: LocalGithubUserDataSourceImpl): LocalGithubUserDataSource

    @Binds
    abstract fun bindGithubRepository(githubRepository: GithubRepositoryImpl): GithubRepository


    companion object {
        // Room
        @Provides
        @Singleton
        fun provideGithubDatabase(@ApplicationContext context: Context): GithubUserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                GithubUserDatabase::class.java,
                "githubUser.db"
            ).build()
        }

        // Dao
        @Provides
        @Singleton
        fun provideGithubDao(
            githubUserDatabase: GithubUserDatabase
        ): GithubUserDao {
            return githubUserDatabase.GithubUserDao()
        }

        @Provides
        @Singleton
        fun provideGithubApi(): GithubApi {
            return GitHubService.getGitHubService()
        }

        @Provides
        @Singleton
        fun provideSearchGithubUser(repository: GithubRepository): SearchGithubUser {
            return SearchGithubUser(repository)
        }

        @Provides
        @Singleton
        fun provideBookmark(repository: GithubRepository): Bookmark {
            return Bookmark(repository)
        }

        @Provides
        @Singleton
        fun provideGithubUserManager(search: SearchGithubUser, bookmark: Bookmark): GithubUserManager {
            return GithubUserManager(search, bookmark)
        }
    }
}
