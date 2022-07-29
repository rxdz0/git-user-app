package com.example.dicoding.submisi2Bfaa.model

import com.google.gson.annotations.SerializedName

data class gitResponse(

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("items")
    val items: List<Users>
)

data class Users(

    @field:SerializedName("login")
    val username: String?,

    @field:SerializedName("company")
    val company: String?,

    @field:SerializedName("id")
    val id: Int?,

    @field:SerializedName("public_repos")
    val repository: Int,

    @field:SerializedName("email")
    val email: Any?,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatar: String?,

    @field:SerializedName("html_url")
    val url: String?,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("location")
    val location: String?
)