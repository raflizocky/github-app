package com.zocky.githubapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zocky.githubapp.data.response.ItemsItem
import com.zocky.githubapp.data.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _users = MutableLiveData<List<ItemsItem>>()
    val users: LiveData<List<ItemsItem>> = _users

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private fun showToast(message: String) {
        _toastMessage.value = message
    }

    fun searchUsers(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = ApiConfig.getApiService().searchUsers(query).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _users.postValue(responseBody.items)
                    }
                } else {
                    showToast("Failed to fetch users. Please try again later.")
                }
            } catch (e: Exception) {
                showToast("An error occurred. Please try again later.")
            }
        }
    }
}

