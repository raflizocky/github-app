package com.zocky.githubapp.data.room.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: FavoriteUser)

    @Update
    fun update(favoriteUser: FavoriteUser)

    @Query("DELETE FROM FavoriteUser WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>

    @Query("SELECT * FROM FavoriteUser")
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>>

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM FavoriteUser WHERE username = :username) THEN 1 ELSE 0 END")
    fun isFavorite(username: String): Int
}
