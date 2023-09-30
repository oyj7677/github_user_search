package com.example.github_data.data.repository.remote

import com.example.github_data.data.remoteItemToDomain
import com.example.github_data.retrofit.GithubApi
import com.example.github_domain.repository.GithubUserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class RemoteGithubUserDataSourceImpl @Inject constructor(private val githubService: GithubApi) :
    RemoteGithubUserDataSource {
    override suspend fun getGithubUserDataByName(name: String): List<GithubUserData> {
        return CoroutineScope(Dispatchers.IO).async {
            val response = githubService.getGitHubUserData(name)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                body.items.takeIf { it.isNotEmpty() }?.remoteItemToDomain() ?: emptyList()
            } else {
                emptyList()
            }
        }.await()
    }
}
