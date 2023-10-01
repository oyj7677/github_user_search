package com.example.github_data.data.repository.local.fake

import com.example.github_data.data.repository.local.LocalGithubUserDataSource
import com.example.github_data.data.room.GithubBookmarkUserEntity
import com.example.github_data.data.entityToDomain
import com.example.github_data.data.toEntity
import com.example.github_domain.repository.GithubUserData

class FakeLocalGithubUserDataSourceImpl :
    LocalGithubUserDataSource {
    private val githubUserDataList = mutableListOf<GithubBookmarkUserEntity>()

    // 현재 name과 login 구분이 어려운 관계로 모든 값을 호출한다.
    override suspend fun getGithubUserDataByName(name: String): List<GithubUserData> {
        return githubUserDataList.entityToDomain()
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        githubUserDataList.add(githubUserData.toEntity())
    }

    override suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        githubUserDataList.removeIf { it.id == githubUserData.id }
    }
}
