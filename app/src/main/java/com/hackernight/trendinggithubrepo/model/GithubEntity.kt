package com.hackernight.trendinggithubrepo.model

import com.google.gson.annotations.SerializedName

data class GithubEntity (
    @SerializedName("author")
    val author:String?,
    @SerializedName("name")
    val name:String?,
    @SerializedName("description")
    val description:String?,
    @SerializedName("language")
    val language:String?,
    @SerializedName("languageColor")
    val languageColor:String?,
    @SerializedName("stars")
    val stars:String?
){ }