package com.example.githubsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.github_domain.DataSourceType
import com.example.github_domain.GithubUserManager
import com.example.github_domain.repository.GithubUserData
import com.example.githubsearch.live_data.ListLiveData
import com.example.githubsearch.live_data.MutableSingleLiveData
import com.example.githubsearch.live_data.SingleLiveData
import com.example.githubsearch.viewpager.UserDataListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUserSearchViewModel @Inject constructor(private val githubUserManager: GithubUserManager) :
    ViewModel() {

    // 검색 시 보여줄 데이터 리스트
    private var _githubUserList = ListLiveData<UserDataListItem>()
    val githubUserList: ListLiveData<UserDataListItem>
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

        viewModelScope.launch(Dispatchers.IO){
            githubUserManager.searchGithubUser(searchWord.value.toString()).also {
                getUserList()
            }
        }
    }

    fun clickItem(userData: UserDataListItem.UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            githubUserManager.updateBookmarkStatus(userData.toGithubUserData()).also {
                getUserList()
            }
        }
    }

    fun setDataSourceType(dataSourceType: DataSourceType) {
        githubUserManager.setDataSourceType(dataSourceType)
        getUserList()
    }

    private fun getUserList() {
        val userDataListItem = createUserDataListItem(githubUserManager.getUserDataList())
        _githubUserList.postValue(userDataListItem)
    }

    private fun createUserDataListItem(githubUserData: List<GithubUserData>): MutableList<UserDataListItem> {
        val userDataListItem = mutableListOf<UserDataListItem>()
        val sortedList = githubUserData.sortedBy { it.name.toLowerCase() }

        for ((i, data) in sortedList.withIndex()) {
            if (i == 0) {
                userDataListItem.add(UserDataListItem.HeaderData(data.name[0].toString()))
                userDataListItem.add(
                    data.toUserDataListItem()
                )
            } else {
                if (sortedList[i].name[0].toLowerCase() != sortedList[i - 1].name[0].toLowerCase()) {
                    userDataListItem.add(UserDataListItem.HeaderData(data.name[0].toString()))
                }
                userDataListItem.add(
                    data.toUserDataListItem()
                )
            }
        }
        return userDataListItem
    }
}
