package com.example.github_data.data.repository.local

import com.example.github_data.data.room.GithubUserDao
import com.example.github_data.data.entityToDomain
import com.example.github_data.data.toEntity
import com.example.github_domain.repository.GithubUserData
import javax.inject.Inject

class LocalGithubUserDataSourceImpl @Inject constructor(
    private val githubUserDao: GithubUserDao
) : LocalGithubUserDataSource {
    override suspend fun getGithubUserDataByName(name: String): List<GithubUserData> {
        return githubUserDao.getGithubRepoByName(name).entityToDomain()
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        githubUserDao.insertGithubRepoEntity(githubUserData.toEntity())
    }

    override suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        githubUserDao.deleteAllGithubRepoById(githubUserData.id)
    }
}
