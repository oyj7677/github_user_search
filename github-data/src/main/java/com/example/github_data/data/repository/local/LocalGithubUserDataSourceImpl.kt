package com.example.github_data.data.repository.local

import com.example.github_data.data.room.GithubUserDao
import com.example.github_data.data.entityToDomain
import com.example.github_data.data.toEntity
import com.example.github_domain.repository.GithubUserData
import javax.inject.Inject

class LocalGithubUserDataSourceImpl @Inject constructor(
    private val githubUserDao: GithubUserDao
) : LocalGithubUserDataSource {

    /*
    * DB에서 데이터를 호출한다.
    * 검색어가 없을 경우 모든 데이터를 가져오고, 검색어가 있을 경우 검색어를 포함하는 데이터만 가져온다.
    * */
    override suspend fun getGithubUserData(searchWord: String): List<GithubUserData> {
        return try {
            if (searchWord.isEmpty()) {
                githubUserDao.getGithubRepo().entityToDomain()
            } else {
                githubUserDao.getGithubRepoByName(searchWord).entityToDomain()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        githubUserDao.insertGithubRepoEntity(githubUserData.toEntity())
    }

    override suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        githubUserDao.deleteAllGithubRepoById(githubUserData.id)
    }
}
