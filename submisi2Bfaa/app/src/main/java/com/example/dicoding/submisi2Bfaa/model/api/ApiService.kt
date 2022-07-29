package com.example.dicoding.submisi2Bfaa.model.api


import com.example.dicoding.submisi2Bfaa.model.FollowResponse
import com.example.dicoding.submisi2Bfaa.model.Users
import com.example.dicoding.submisi2Bfaa.model.gitResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @Headers("Authorization: token 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")
    @GET("search/users")
    fun getUser(
        @Query("q") query: String?,
    ): Call<gitResponse>

    @Headers("Authorization: token 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")
    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")
        username: String?
    ): Call<Users>

    @Headers("Authorization: token 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")
    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username")
        username: String?
    ): Call<ArrayList<FollowResponse>>

    @Headers("Authorization: token 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")
    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username")
        username: String?
    ): Call<ArrayList<FollowResponse>>
}