package com.example.github_data.data.repository.local

import com.example.github_data.data.room.GithubUserDao
import com.example.github_data.data.entityToDomain
import com.example.github_data.data.toEntity
import com.example.github_domain.GithubUserData

class LocalGithubUserDataSourceImpl(private val githubUserDao: GithubUserDao): LocalGithubUserDataSource {
    override suspend fun getGithubUserDataByName(name: String): List<GithubUserData> {
        return githubUserDao.getGithubRepoByName(name).entityToDomain()
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        githubUserDao.insertGithubRepoEntity(githubUserData.toEntity())
    }

    override suspend fun deleteGithubUserDataById(id: Int) {
       githubUserDao.deleteAllGithubRepoById(id)
    }
}
