package com.hackernight.trendinggithubrepo.model

import io.reactivex.Single
import retrofit2.http.GET

interface TrendingRepoApi {
    @GET("repositories")
    fun getTrendingRepo():Single<List<GithubEntity>>
}