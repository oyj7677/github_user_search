package com.example.github_domain

import com.example.github_domain.repository.GithubRepository
import com.example.github_domain.repository.GithubUserData
import javax.inject.Inject

class SearchGithubUser @Inject constructor(private val githubRepository: GithubRepository) {
    // Github 사용자 검색 담당
    private var _dataSourceType: DataSource = Api
    val dataSourceType: DataSource
        get() = _dataSourceType


    // API 검색
    suspend fun searchGithubUser(searchWord: String): List<GithubUserData> {
        return _dataSourceType.getSearchedUserList(githubRepository, searchWord)
    }

    // 데이터 소스 타입
    fun setDataSourceType(dataSourceType: DataSourceType) {
        _dataSourceType = when (dataSourceType) {
            DataSourceType.API -> Api
            DataSourceType.LOCAL -> Local
        }
    }
}
