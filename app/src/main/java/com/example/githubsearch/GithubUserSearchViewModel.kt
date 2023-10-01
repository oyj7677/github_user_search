package com.example.githubsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_domain.DataSourceType
import com.example.github_domain.Search
import com.example.github_domain.repository.GithubRepository
import com.example.github_domain.repository.GithubUserData
import com.example.githubsearch.live_data.ListLiveData
import com.example.githubsearch.live_data.MutableSingleLiveData
import com.example.githubsearch.live_data.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUserSearchViewModel @Inject constructor(private val search: Search) : ViewModel() {

    // 검색 시 보여줄 데이터 리스트
    private var _githubUserList = ListLiveData<GithubUserData>()
    val githubUserList: ListLiveData<GithubUserData>
        get() = _githubUserList

    // 검색어
    private var _searchWord = MutableLiveData<String>()
    val searchWord: MutableLiveData<String>
        get() = _searchWord

    private val _clickSearch = MutableSingleLiveData<Any>()
    val clickSearch: SingleLiveData<Any>
            get() = _clickSearch

    // 검색 로직
    fun searchGithubUser() {
        _clickSearch.postValue(Any())

        CoroutineScope(Dispatchers.IO).launch {
            search.searchGithubUser(searchWord.value.toString()).also {
                getUserList()
            }
        }
    }

    fun clickItem(githubUserData: GithubUserData) {
        CoroutineScope(Dispatchers.IO).launch {
            search.updateBookmarkStatus(githubUserData).also {
                getUserList()
            }
        }
    }

    fun setDataSourceType(dataSourceType: DataSourceType) {
        search.setDataSourceType(dataSourceType)
        getUserList()
    }

    private fun getUserList() {
        _githubUserList.postValue(search.getUserDataList().toMutableList())
    }
}
