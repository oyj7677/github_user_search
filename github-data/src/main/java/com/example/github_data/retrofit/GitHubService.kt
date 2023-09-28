package com.example.github_data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object GitHubService {
    private const val BASE_URL = "https://api.github.com"

    fun getGitHubService(): GithubApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi::class.java)
    }
}
