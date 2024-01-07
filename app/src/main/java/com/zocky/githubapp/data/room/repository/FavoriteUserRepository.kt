package com.zocky.githubapp.data.room.repository

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import com.zocky.githubapp.data.room.db.FavoriteRoomDatabase
import com.zocky.githubapp.data.room.db.FavoriteUser
import com.zocky.githubapp.data.room.db.FavoriteUserDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mFavoriteUserDao.getFavoriteUserByUsername(username)
    }

    fun insert(favoriteUser: FavoriteUser) {
        executorService.execute {
            try {
                mFavoriteUserDao.insert(favoriteUser)
                Log.d(TAG, "Insert Success: $favoriteUser")
            } catch (e: Exception) {
                Log.e(TAG, "Insert Error: ${e.message}")
            }
        }
    }

    fun delete(username: String) {
        executorService.execute {
            try {
                mFavoriteUserDao.delete(username)
                Log.d(TAG, "Delete Success: ")
            } catch (e: Exception) {
                Log.e(TAG, "Delete Error: ${e.message}")
            }
        }
    }

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> = mFavoriteUserDao.getAllFavoriteUsers()

    fun isFavorite(username: String): Int {
        return mFavoriteUserDao.isFavorite(username)
    }

}
