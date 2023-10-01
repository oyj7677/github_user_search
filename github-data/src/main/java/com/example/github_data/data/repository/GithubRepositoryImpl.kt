package com.example.github_data.data.repository

import com.example.github_data.data.repository.local.LocalGithubUserDataSource
import com.example.github_data.data.repository.remote.RemoteGithubUserDataSource
import com.example.github_domain.repository.GithubUserData
import com.example.github_domain.repository.GithubRepository
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val remoteGithubUserDataSource: RemoteGithubUserDataSource,
    private val localGithubUserDataSource: LocalGithubUserDataSource
) : GithubRepository {
    override suspend fun getGithubUserDataByName(name: String): List<GithubUserData> {
        val bookmarkUserData = localGithubUserDataSource.getGithubUserDataByName(name)
        val remoteUserData = remoteGithubUserDataSource.getGithubUserDataByName(name)

        return remoteUserData.map { remoteData ->
            val isBookmark = bookmarkUserData.any { bookmarkData ->
                bookmarkData.id == remoteData.id
            }
            remoteData.copy(isBookmark = isBookmark)
        }
    }

    override suspend fun getBookmarkGithubUserDataByName(name: String): List<GithubUserData> {
        return localGithubUserDataSource.getGithubUserDataByName(name)
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        localGithubUserDataSource.insertGithubUserData(githubUserData)
    }

    override suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        localGithubUserDataSource.deleteGithubUserData(githubUserData)
    }
}
