package com.example.github_domain.repository

data class GithubUserData(
    val id: Int,
    val name: String,
    val profileImg: String,
    var isBookmark: Boolean
)
