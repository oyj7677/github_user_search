package com.example.githubsearch

import com.example.github_domain.repository.GithubUserData
import com.example.githubsearch.viewpager.UserDataListItem

fun UserDataListItem.UserData.toGithubUserData(): GithubUserData {
    return GithubUserData(
        id = id,
        name = name,
        profileImg = profileImg,
        isBookmark = isBookmark
    )
}

fun GithubUserData.toUserDataListItem(): UserDataListItem.UserData {
    return UserDataListItem.UserData(
        id = id,
        name = name,
        profileImg = profileImg,
        isBookmark = isBookmark
    )
}
