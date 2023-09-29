package com.example.github_data.data.repository

import com.example.github_data.data.repository.local.LocalGithubUserDataSource
import com.example.github_data.data.repository.remote.RemoteGithubUserDataSource
import com.example.github_domain.GithubUserData
import com.example.github_domain.GithubUserRepository

class GithubRepositoryImpl(
    private val remoteGithubUserDataSource: RemoteGithubUserDataSource,
    private val localGithubUserDataSource: LocalGithubUserDataSource
) : GithubUserRepository {
    override suspend fun getGithubUserDataByName(
        name: String,
        isLocal: Boolean
    ): List<GithubUserData> {
        // 로컬 데이터와 비교하여 즐겨찾기 여부 확인
        val bookmarkUserData = localGithubUserDataSource.getGithubUserDataByName(name)
        return if (isLocal) {
            bookmarkUserData
        } else {
            val remoteUserData = remoteGithubUserDataSource.getGithubUserDataByName(name)
            remoteUserData.map { remoteData ->
                val isBookmark = bookmarkUserData.any { bookmarkData ->
                    bookmarkData.id == remoteData.id
                }
                remoteData.copy(isBookmark = isBookmark)
            }
        }
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        localGithubUserDataSource.insertGithubUserData(githubUserData)
    }

    override suspend fun deleteGithubUserDataById(id: Int) {
        localGithubUserDataSource.deleteGithubUserDataById(id)
    }

}
