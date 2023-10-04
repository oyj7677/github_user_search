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
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GithubUserSearchViewModel @Inject constructor(private val githubUserManager: GithubUserManager) :
    ViewModel() {

    // 검색 시 보여줄 데이터 리스트
    private var _githubUserList = ListLiveData<UserDataListItem>()
    val githubUserList: ListLiveData<UserDataListItem>
        get() = _githubUserList

    // 검색어
    private var _searchWord = MutableLiveData("")
    val searchWord: MutableLiveData<String>
        get() = _searchWord

    private val _clickSearch = MutableSingleLiveData<Any>()
    val clickSearch: SingleLiveData<Any>
        get() = _clickSearch

    private val _toastEvent = MutableSingleLiveData<Int>()
    val toastEvent: SingleLiveData<Int>
        get() = _toastEvent

    /*
    * Github user를 검색하는 앰의 핵심 함수입니다.
    * viewModel에서 githubUserManager.searchGithubUser()를 호출하여 데이터를 가져옵니다.
    * 세팅되어있는 dataSourceType에 의해 API 또는 DB에서 데이터를 가져옵니다.
    * */
    fun searchGithubUser() {
        _clickSearch.postValue(Any())

        val dataSourceType = githubUserManager.getDataSourceType()
        if (searchWord.value.isNullOrEmpty() && dataSourceType == DataSourceType.API) {
            _toastEvent.postValue(R.string.ERR_MSG_EMPTY_API_SEARCH_WORD)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            githubUserManager.searchGithubUser(searchWord.value.toString()).also {
                getUserList()
            }
        }
    }

    /*
    * 리스트를 클릭했을때의 이벤트입니다.
    * githubUserManager.updateBookmarkStatus()를 호출하여 DB에 저장 또는 삭제합니다.
    * */
    fun clickItem(userData: UserDataListItem.UserData) {
        viewModelScope.launch(Dispatchers.IO) {
            githubUserManager.updateBookmarkStatus(userData.toGithubUserData()).also {
                getUserList()
            }
        }
    }

    /*
    * 호출할 데이터의 타입을 세팅합니다.
    * DataSourceType.API: API에서 데이터를 가져옵니다.
    * DataSourceType.LOCAL: DB에서 데이터를 가져옵니다.
    * */
    fun setDataSourceType(dataSourceType: DataSourceType) {
        githubUserManager.setDataSourceType(dataSourceType)
        getUserList()
    }

    private fun getUserList() {
        val userDataListItem = createUserDataListItem(githubUserManager.getUserDataList())
        if (userDataListItem.isEmpty()) {
            _toastEvent.postValue(R.string.ERR_MSG_EMPTY_SEARCH_RESULT)
            _githubUserList.postValue(userDataListItem)
        } else {
            _githubUserList.postValue(userDataListItem)
        }
    }

    /*
    * 리스트의 아이템을 생성하는 함수입니다.
    * 리스트를 정렬하고, 이름의 첫글자가 다를 경우에는 헤더를 추가합니다.
    * */
    private fun createUserDataListItem(githubUserData: List<GithubUserData>): MutableList<UserDataListItem> {
        val userDataListItem = mutableListOf<UserDataListItem>()
        val sortedList = githubUserData.sortedBy { it.name.lowercase(Locale.getDefault()) }

        for ((i, data) in sortedList.withIndex()) {
            if (i == 0) {
                userDataListItem.add(UserDataListItem.HeaderData(data.name[0].toString()))
                userDataListItem.add(
                    data.toUserDataListItem()
                )
            } else {
                if (sortedList[i].name[0].lowercaseChar() != sortedList[i - 1].name[0].lowercaseChar()) {
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
