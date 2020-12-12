package com.hackernight.trendinggithubrepo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IGithubEntityDao {
    @Insert
    suspend fun insertAll(vararg githubEntity: GithubEntity)

    @Query("Select * from githubentity")
    suspend fun getAllGithubEntity() : List<GithubEntity>

    @Query("Delete from githubentity")
    suspend fun deleteGithubEntity()
}