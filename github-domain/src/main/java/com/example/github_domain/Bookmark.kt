package com.example.github_domain

import com.example.github_domain.repository.GithubRepository
import com.example.github_domain.repository.GithubUserData
import javax.inject.Inject

class Bookmark @Inject constructor(private val githubRepository: GithubRepository) {
    // 아이템 선택
    suspend fun updateBookmarkStatus(githubUserData: GithubUserData) {
        if (githubUserData.isBookmark) {
            deleteGithubUserData(githubUserData)
        } else {
            insertGithubUserData(githubUserData)
        }
    }

    // DB 저장
    private suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        githubRepository.insertGithubUserData(githubUserData)
    }

    // DB 삭제
    private suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        githubRepository.deleteGithubUserData(githubUserData)
    }

}
