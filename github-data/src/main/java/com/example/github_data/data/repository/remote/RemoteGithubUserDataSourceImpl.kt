package com.example.github_data.data.repository.remote

import com.example.github_data.data.remoteItemToDomain
import com.example.github_data.retrofit.GithubApi
import com.example.github_domain.repository.GithubUserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import javax.inject.Inject

class RemoteGithubUserDataSourceImpl @Inject constructor(private val githubService: GithubApi) :
    RemoteGithubUserDataSource {

    /*
    * api호출을 통해 데이터를 가져온다.
    * query의 조건에 in: login type: user를 추가하여 검색어를 포함하는 사용자만 가져오도록 한다.
    * 통신이 실패했을 경우 빈 리스트를 반환한다.
    * */
    override suspend fun getGithubUserDataByName(searchWord: String): List<GithubUserData> {
        return CoroutineScope(Dispatchers.IO).async {
            try {
                val query = "$searchWord in:login type:user"
                val response = githubService.getGitHubUserData(query)
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    body.items.takeIf { it.isNotEmpty() }?.remoteItemToDomain() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }.await()
    }
}
