package com.djw.mygithubuserapp.database.remote.retrofit

import com.djw.mygithubuserapp.database.remote.response.ItemsItem
import com.djw.mygithubuserapp.database.remote.response.SearchResponse
import com.djw.mygithubuserapp.database.remote.response.DetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getSearch(
        @Query("q") username: String
    ): Call<SearchResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailResponse

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}