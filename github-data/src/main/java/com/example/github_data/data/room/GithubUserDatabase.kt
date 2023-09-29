package com.example.github_data.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GithubBookmarkUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GithubUserDatabase: RoomDatabase() {
    abstract fun GithubUserDao(): GithubUserDao

    companion object {
        private var instance: GithubUserDatabase? = null

        @Synchronized
        fun getInstance(context: Context): GithubUserDatabase? {
            if (instance == null)
                synchronized(GithubUserDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubUserDatabase::class.java,
                        "githubUserDatabase.db"
                    ).build()
                }
            return instance
        }
    }
}
