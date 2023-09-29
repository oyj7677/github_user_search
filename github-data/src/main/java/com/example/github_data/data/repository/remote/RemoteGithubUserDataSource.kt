package com.example.github_data.data.repository.remote

import com.example.github_domain.GithubUserData

interface RemoteGithubUserDataSource {
    suspend fun getGithubUserDataByName(name: String): List<GithubUserData>
}
