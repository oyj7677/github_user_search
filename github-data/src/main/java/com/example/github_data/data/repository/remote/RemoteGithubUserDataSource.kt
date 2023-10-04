package com.example.github_data.data.repository.remote

import com.example.github_domain.repository.GithubUserData

interface RemoteGithubUserDataSource {
    suspend fun getGithubUserDataByName(searchWord: String): List<GithubUserData>
}
