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

    /*
    * api를 통해 데이터를 가져오고, 로컬 데이터(즐겨찾기)와 비교하여 즐겨찾기 여부를 결정한다.
    * */
    override suspend fun getGithubUserDataByName(searchWord: String): List<GithubUserData> {
        val bookmarkUserData = localGithubUserDataSource.getGithubUserData(searchWord)
        val remoteUserData = remoteGithubUserDataSource.getGithubUserDataByName(searchWord)

        return remoteUserData.map { remoteData ->
            val isBookmark = bookmarkUserData.any { bookmarkData ->
                bookmarkData.id == remoteData.id
            }
            remoteData.copy(isBookmark = isBookmark)
        }
    }

    override suspend fun getBookmarkGithubUserData(searchWord: String): List<GithubUserData> {
        return localGithubUserDataSource.getGithubUserData(searchWord)
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        localGithubUserDataSource.insertGithubUserData(githubUserData)
    }

    override suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        localGithubUserDataSource.deleteGithubUserData(githubUserData)
    }
}
