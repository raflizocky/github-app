package com.zocky.githubapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zocky.githubapp.data.response.DetailResponse
import com.zocky.githubapp.data.retrofit.ApiConfig
import com.zocky.githubapp.data.room.db.FavoriteUser
import com.zocky.githubapp.data.room.repository.FavoriteUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: FavoriteUserRepository = FavoriteUserRepository(application)

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    fun getUserDetail(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().getUserDetail(username).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userDetail.postValue(responseBody)
                    } else {
                        Log.e(TAG, "getUserDetail: Response body is null")
                    }
                } else {
                    Log.e(TAG, "getUserDetail: Response is not successful")
                }
            } catch (e: Exception) {
                Log.e(TAG, "getUserDetail: Error - ${e.message}")
            }
        }
    }

    fun checkFavoriteStatus(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isUserInFavorites = repository.isFavorite(username)
                _isFavorite.postValue(isUserInFavorites == 1) // Convert to Boolean
            } catch (e: Exception) {
                Log.e(TAG, "checkFavoriteStatus: Error - ${e.message}")
            }
        }
    }

    fun toggleFavoriteStatus(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val isFavorite = _isFavorite.value ?: false  // Use the LiveData value

                if (isFavorite) {
                    repository.delete(username)
                    Log.d(TAG, "User removed from favorites: $username")
                } else {
                    val userDetail = userDetail.value
                    if (userDetail != null) {
                        val isUserInFavorites =
                            repository.getFavoriteUserByUsername(username).value != null

                        if (!isUserInFavorites) {
                            val newFavoriteUser = FavoriteUser(
                                username = userDetail.login,
                                avatarUrl = userDetail.avatarUrl
                            )
                            repository.insert(newFavoriteUser)
                            Log.d(TAG, "User added to favorites: ${userDetail.login}")
                        }
                    }
                }

                // Toggle isFavorite
                _isFavorite.postValue(!isFavorite)
            } catch (e: Exception) {
                Log.e(TAG, "Toggle Favorite Error: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}

