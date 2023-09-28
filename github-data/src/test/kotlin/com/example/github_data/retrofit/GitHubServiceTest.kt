package com.example.github_data.retrofit

import com.example.github_data.data.repository.remote.RemoteGithubUserData
import com.google.common.truth.Truth
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class GitHubServiceTest {
    private val server = MockWebServer()
    private lateinit var service: GithubApi

    @Before
    fun init() {
        val baseUrl = server.url("https://api.github.com")

        service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create()
    }

    @Test
    fun `getGitHubUserData("ho")를 요청할 때, githubUserData_ho_json과 같은 값을 응답받는다`() = runTest(UnconfinedTestDispatcher()) {
        // given : githubUserData_ho.json 파일을 읽어서 RemoteGithubUserData로 변환한다.
        val sampleData = File("src/test/resources/githubUserData_ho.json").readText()
        val expected = Gson().fromJson(sampleData, RemoteGithubUserData::class.java)

        // when : getGitHubUserData("ho")를 요청한다.
        val actual = service.getGitHubUserData("ho").body()

        // then : githubUserData_ho.json과 같은 값을 응답받는다.
        Truth.assertThat(actual).isEqualTo(expected)
    }
}
