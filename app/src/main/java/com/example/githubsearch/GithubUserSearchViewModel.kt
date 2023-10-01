package com.example.githubsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_domain.DataSourceType
import com.example.github_domain.repository.GithubRepository
import com.example.github_domain.repository.GithubUserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUserSearchViewModel @Inject constructor(private val gitHubRepository: GithubRepository) : ViewModel() {

    // 검색 시 보여줄 데이터 리스트
    private var _githubUserList = ListLiveData<GithubUserData>()
    val githubUserList: ListLiveData<GithubUserData>
        get() = _githubUserList

    // 검색어
    private var _query = MutableLiveData<String>()
    val query: MutableLiveData<String>
        get() = _query

    private var _dataSourceType = MutableLiveData<DataSourceType>()
    val dataSourceType: MutableLiveData<DataSourceType>
        get() = _dataSourceType

    // 검색 로직
    fun searchGithubUser() {
        CoroutineScope(Dispatchers.IO).launch {
            gitHubRepository.getGithubUserDataByName(query.value.toString()).also {
                _githubUserList.postValue(it.toMutableList())
            }
        }
    }
}
