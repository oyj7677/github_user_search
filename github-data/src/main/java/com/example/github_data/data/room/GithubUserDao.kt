package com.example.github_data.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GithubUserDao {
    @Insert
    fun insertGithubRepoEntity(githubBookmarkUserEntity: GithubBookmarkUserEntity)

    @Query("SELECT * FROM githubUser where name LIKE '%' || :name || '%'")
    fun getGithubRepoByName(name: String): List<GithubBookmarkUserEntity>

    @Query("SELECT * FROM githubUser")
    fun getGithubRepo(): List<GithubBookmarkUserEntity>


    @Query("DELETE FROM githubUser where id = :id")
    fun deleteAllGithubRepoById(id: Int)


}
