package com.example.github_domain

interface GithubUserRepository {
    suspend fun getGithubUserDataByName(name: String, isLocal: Boolean): List<GithubUserData>

    suspend fun insertGithubUserData(githubUserData: GithubUserData)

    suspend fun deleteGithubUserDataById(id: Int)
}
