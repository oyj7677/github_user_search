package com.example.github_domain

interface GithubUserRepository {
    suspend fun getGithubUserDataByName(name: String): List<GithubUserData>

    suspend fun getBookmarkGithubUserDataByName(name: String): List<GithubUserData>

    suspend fun insertGithubUserData(githubUserData: GithubUserData)

    suspend fun deleteGithubUserDataById(id: Int)
}
