package com.zocky.githubapp.data.retrofit

import com.zocky.githubapp.data.response.DetailResponse
import com.zocky.githubapp.data.response.GithubResponse
import com.zocky.githubapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}
