package com.zocky.githubapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zocky.githubapp.data.room.db.FavoriteUser
import com.zocky.githubapp.data.room.repository.FavoriteUserRepository

class FavoriteViewModel(private val repository: FavoriteUserRepository) : ViewModel() {
    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {
        return repository.getAllFavoriteUsers()
    }
}
