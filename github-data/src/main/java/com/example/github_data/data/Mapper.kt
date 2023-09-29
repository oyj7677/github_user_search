package com.example.github_data.data

import com.example.github_data.data.repository.remote.data.Item
import com.example.github_data.data.room.GithubBookmarkUserEntity
import com.example.github_domain.GithubUserData


// local to domain
fun List<GithubBookmarkUserEntity>.entityToDomain(): List<GithubUserData> {
    return this.toList().map {
        GithubUserData(
            id = it.id,
            name = it.name,
            profileImg = it.profileImg,
            isBookmark = true
        )
    }
}

// remote to domain
fun List<Item>.remoteItemToDomain(): List<GithubUserData> {
    return this.toList().map {
        GithubUserData(
            id = it.id,
            name = it.login,
            profileImg = it.avatarUrl,
            isBookmark = false
        )
    }
}

fun GithubUserData.toEntity(): GithubBookmarkUserEntity {
    return GithubBookmarkUserEntity(
        id = this.id,
        name = this.name,
        profileImg = this.profileImg
    )
}
