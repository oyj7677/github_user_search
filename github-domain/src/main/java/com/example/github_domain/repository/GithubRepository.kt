package com.example.github_domain.repository

interface GithubRepository {
    suspend fun getGithubUserDataByName(searchWord: String): List<GithubUserData>

    suspend fun getBookmarkGithubUserData(searchWord: String): List<GithubUserData>

    suspend fun insertGithubUserData(githubUserData: GithubUserData)

    suspend fun deleteGithubUserData(githubUserData: GithubUserData)
}
