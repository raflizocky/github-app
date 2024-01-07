package com.zocky.githubapp.ui.detail.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zocky.githubapp.data.response.ItemsItem
import com.zocky.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val TAG = "FollowViewModel"

    fun getFollowers(username: String) {
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _followers.value = responseBody
                    }
                } else {
                    handleApiError(response)
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                handleApiFailure(t)
            }
        })
    }

    fun getFollowing(username: String) {
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _following.value = responseBody
                    }
                } else {
                    handleApiError(response)
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                handleApiFailure(t)
            }
        })
    }

    private fun handleApiError(response: Response<List<ItemsItem>>) {
        val errorMessage = response.message()
        Log.e(TAG, "API call failed: $errorMessage")
    }

    private fun handleApiFailure(t: Throwable) {
        val errorMessage = t.localizedMessage ?: "Unknown error"
        Log.e(TAG, "API call failed: $errorMessage")
    }
}

