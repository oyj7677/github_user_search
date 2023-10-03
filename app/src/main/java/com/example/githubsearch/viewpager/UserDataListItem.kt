package com.example.githubsearch.viewpager

sealed class UserDataListItem {
    data class UserData(
        val id: Int,
        val name: String,
        val profileImg: String,
        var isBookmark: Boolean
    ) : UserDataListItem()

    data class HeaderData(
        val title: String
    ) : UserDataListItem()
}

