package com.example.github_domain

data class GithubUserData(
    val id: Int,
    val name: String,
    val profileImg: String,
    var isBookmark: Boolean
)
