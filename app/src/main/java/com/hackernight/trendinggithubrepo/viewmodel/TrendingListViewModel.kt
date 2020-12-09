package com.hackernight.trendinggithubrepo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackernight.trendinggithubrepo.model.GithubEntity

class TrendingListViewModel : ViewModel() {

    val trendingRepoList = MutableLiveData<List<GithubEntity>>()
    val trendingRepoError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val repo1 = GithubEntity("Amit","Jetpack","A suite libraries","Kotlin","#F3444","34")
        val repo2 = GithubEntity("Sumit","Jetpack","A suite libraries","Kotlin","#F3444","34")
        val repo3 = GithubEntity("Akili","Jetpack","A suite libraries","Kotlin","#F3444","34")
        val repo4 = GithubEntity("Avila","Jetpack","A suite libraries","Kotlin","#F3444","34")

        val repoList = arrayListOf<GithubEntity>(repo1,repo2,repo3,repo4)

        trendingRepoList.value = repoList
        trendingRepoError.value = false
        loading.value = false
    }
}