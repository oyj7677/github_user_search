package com.example.github_data.data.repository.remote.data


import com.google.gson.annotations.SerializedName

data class RemoteGithubUserData(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("total_count")
    val totalCount: Int
)
