package com.example.github_data

import com.example.github_data.data.repository.GithubRepositoryImpl
import com.example.github_data.data.repository.local.fake.FakeLocalGithubUserDataSourceImpl
import com.example.github_data.data.repository.remote.RemoteGithubUserDataSourceImpl
import com.example.github_data.retrofit.GithubApi
import com.example.github_domain.repository.GithubUserData
import com.example.github_domain.repository.GithubRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@OptIn(ExperimentalCoroutinesApi::class)
class GithubRepositoryImplTest {
    // TODO: GithubRepositoryImplTest
    // 통신 및 데이터 저장
    private val server = MockWebServer()
    private lateinit var service: GithubApi
    private  lateinit var repository: GithubRepository

    @Before
    fun setUp() {
        val baseUrl = server.url("https://api.github.com")

        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create()

        repository = GithubRepositoryImpl(
            remoteGithubUserDataSource = RemoteGithubUserDataSourceImpl(service),
            localGithubUserDataSource = FakeLocalGithubUserDataSourceImpl()
        )
    }


    @Test
    fun `repository_getGithubUserDataByName("ho", false)를 통해 데이터를 받을 때, 응답값의 첫번째 값을 localData에 저장한다`() = runTest(UnconfinedTestDispatcher()) {
            // given : GithubApi.getGitHubUserData("ho")를 통해 데이터를 가져온다.
            val remoteData = repository.getGithubUserDataByName("ho")

            // when : items의 첫번째 값을 localData에 저장한다.
            // localData에 저장할 때, isBookmark = true로 저장한다.
            val actual = remoteData[0].copy(isBookmark = true)
            repository.insertGithubUserData(actual)

            // then : localData에 저장된 값과 items의 첫번째 값이 같다.
            assertThat(actual).isEqualTo(repository.getBookmarkGithubUserDataByName("ho")[0])
        }

    @Test
    fun `GithubApi_getGitHubUserData()요청이 실패했을 때, emptyList()를 응답 받는다`() = runTest(UnconfinedTestDispatcher()) {
        // when : GithubApi.getGitHubUserData() 요청이 실패했을 때,
        val actual = repository.getGithubUserDataByName("")

        // then : emptyList()를 응답 받는다.
        assertThat(actual).isEqualTo(emptyList<GithubUserData>())
    }

    @Test
    fun `GithubApi_getGitHubUserData("ho", false)요청했을 때, 즐겨찾기 됐던 데이터는 isBookmark == true이다`() = runTest(UnconfinedTestDispatcher()) {
        // given : GithubApi.getGitHubUserData("ho", false)를 통해 가져온 데이터 중 remoteData[1]을 즐겨찾기에 추가한다.
        val remoteData = repository.getGithubUserDataByName("ho")
        repository.insertGithubUserData(remoteData[1])

        // when : GithubApi.getGitHubUserData("ho", false)를 호출한다.
       val actual = repository.getGithubUserDataByName("ho")

        // then : 즐겨찾기에 추가했던 값의 isBookmark == true이다.
        assertThat(actual[1].isBookmark).isTrue()
    }
}
