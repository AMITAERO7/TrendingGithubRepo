package com.hackernight.trendinggithubrepo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GithubEntity (
    @ColumnInfo
    @SerializedName("author")
    val author:String?,

    @ColumnInfo
    @SerializedName("name")
    val name:String?,

    @ColumnInfo
    @SerializedName("description")
    val description:String?,

    @ColumnInfo
    @SerializedName("language")
    val language:String?,

    @ColumnInfo
    @SerializedName("languageColor")
    val languageColor:String?,

    @ColumnInfo
    @SerializedName("stars")
    val stars:String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}