package com.example.github_data.data.repository.local

import com.example.github_domain.repository.GithubUserData

interface LocalGithubUserDataSource {
    suspend fun getGithubUserData(name: String): List<GithubUserData>

    suspend fun insertGithubUserData(githubUserData: GithubUserData)

    suspend fun deleteGithubUserData(githubUserData: GithubUserData)
}
