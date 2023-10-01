package com.example.github_domain

import com.example.github_domain.repository.GithubRepository
import com.example.github_domain.repository.GithubUserData
import javax.inject.Inject

class Search @Inject constructor(private val repository: GithubRepository) {

    private var _remoteUserDataList = listOf<GithubUserData>()
    private val remoteUserDataList: List<GithubUserData>
        get() = _remoteUserDataList

    private var _localUserDataList = listOf<GithubUserData>()
    private val localUserDataList: List<GithubUserData>
        get() = _localUserDataList

    private var _dataSourceType: DataSource = Api
    val dataSourceType: DataSource
        get() = _dataSourceType

    // API 검색
    suspend fun searchGithubUser(searchWord: String) {
        _dataSourceType.getSearchedUserList(repository, searchWord).also {
           when(_dataSourceType) {
                Api -> {
                     _remoteUserDataList = it
                }
                Local -> {
                     _localUserDataList = it
                }
           }
        }
    }

    // 아이템 선택
    suspend fun updateBookmarkStatus(githubUserData: GithubUserData) {
        if (githubUserData.isBookmark) {
            deleteGithubUserData(githubUserData)
        } else {
            insertGithubUserData(githubUserData)
        }

        _remoteUserDataList = _remoteUserDataList.map {
            if (it.id == githubUserData.id) {
                it.copy(isBookmark = !it.isBookmark)
            } else {
                it
            }
        }
    }

    // DB 저장
    private suspend fun insertGithubUserData(githubUserData: GithubUserData) {
        repository.insertGithubUserData(githubUserData)
    }

    // DB 삭제
    private suspend fun deleteGithubUserData(githubUserData: GithubUserData) {
        repository.deleteGithubUserData(githubUserData)
    }

    fun getUserDataList(): List<GithubUserData> {
        return when(_dataSourceType) {
            Api -> remoteUserDataList
            Local -> localUserDataList
        }
    }

    // 데이터 소스 타입
    fun setDataSourceType(dataSourceType: DataSourceType) {
        _dataSourceType = when(dataSourceType) {
            DataSourceType.API -> Api
            DataSourceType.LOCAL -> Local
        }
    }
}
