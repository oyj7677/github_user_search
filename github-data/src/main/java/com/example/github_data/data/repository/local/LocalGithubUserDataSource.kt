package com.example.github_data.data.repository.local

import com.example.github_domain.GithubUserData

interface LocalGithubUserDataSource {
    suspend fun getGithubUserDataByName(name: String): List<GithubUserData>

    suspend fun insertGithubUserData(githubUserData: GithubUserData)

    suspend fun deleteGithubUserDataById(id: Int)
}