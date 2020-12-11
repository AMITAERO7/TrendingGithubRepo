package com.hackernight.trendinggithubrepo.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TrendingRepoApiService {

    private val BASE_URL = "https://private-anon-b9b517f62c-githubtrendingapi.apiary-mock.com"

    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(TrendingRepoApi::class.java)

    fun getRepos():Single<List<GithubEntity>>{
        return api.getTrendingRepo()
    }

}