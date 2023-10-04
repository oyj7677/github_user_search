package com.example.github_domain

import com.example.github_domain.repository.GithubUserData
import javax.inject.Inject

class GithubUserManager @Inject constructor(
    private val search: SearchGithubUser,
    private val bookmark: Bookmark
) {

    private var _remoteUserDataList = mutableListOf<GithubUserData>()
    private val remoteUserDataList: List<GithubUserData>
        get() = _remoteUserDataList

    private var _localUserDataList = mutableListOf<GithubUserData>()
    private val localUserDataList: List<GithubUserData>
        get() = _localUserDataList

    /*
    * Github user 검색을 수행합니다.
    * search.dataSourceType에 따라 api 또는 local db에서 데이터를 호출하고 저장합니다.
    * */
    suspend fun searchGithubUser(searchWord: String) {
        when (search.dataSourceType) {
            Api -> {
                _remoteUserDataList = search.searchGithubUser(searchWord).toMutableList()
            }

            Local -> {
                _localUserDataList = search.searchGithubUser(searchWord).toMutableList()
            }
        }
    }

    fun getUserDataList(): List<GithubUserData> {
        return when (search.dataSourceType) {
            Api -> remoteUserDataList
            Local -> localUserDataList
        }
    }

    fun setDataSourceType(dataSourceType: DataSourceType) {
        search.setDataSourceType(dataSourceType)
    }

    fun getDataSourceType(): DataSourceType {
        return when (search.dataSourceType) {
            Api -> DataSourceType.API
            Local -> DataSourceType.LOCAL
        }
    }

    /*
    * Github user의 즐겨찾기 상태를 업데이트합니다.
    * */
    suspend fun updateBookmarkStatus(githubUserData: GithubUserData) {
        bookmark.updateBookmarkStatus(githubUserData)

        if(githubUserData.isBookmark) {
            _localUserDataList.remove(githubUserData)
        }

        _remoteUserDataList = _remoteUserDataList.map {
            if (it.id == githubUserData.id) {
                it.copy(isBookmark = !it.isBookmark)
            } else {
                it
            }
        }.toMutableList()
    }
}
