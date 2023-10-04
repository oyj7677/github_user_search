package com.example.github_domain

import com.example.github_domain.repository.GithubRepository
import com.example.github_domain.repository.GithubUserData

sealed class DataSource: SearchData

data object Api : DataSource() {
    override suspend fun getSearchedUserList(repository: GithubRepository, searchWord: String): List<GithubUserData> {
        return repository.getGithubUserDataByName(searchWord)
    }
}

data object Local : DataSource() {
    override suspend fun getSearchedUserList(repository: GithubRepository, searchWord: String): List<GithubUserData> {
        return repository.getBookmarkGithubUserData(searchWord)
    }
}

internal interface SearchData {
    suspend fun getSearchedUserList(repository: GithubRepository, searchWord: String): List<GithubUserData>
}
