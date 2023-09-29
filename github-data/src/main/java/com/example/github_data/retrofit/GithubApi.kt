package com.example.github_data.retrofit

import com.example.github_data.data.repository.remote.data.RemoteGithubUserData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/search/users")
    suspend fun getGitHubUserData(
        @Query("q") keywords: String,
        @Query("per_page") perPage: Int = 100,
        @Query("page") page: Int = 1
    ): Response<RemoteGithubUserData>
}
