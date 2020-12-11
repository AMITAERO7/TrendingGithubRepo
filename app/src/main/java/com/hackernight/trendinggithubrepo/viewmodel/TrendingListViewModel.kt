package com.hackernight.trendinggithubrepo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackernight.trendinggithubrepo.model.GithubEntity
import com.hackernight.trendinggithubrepo.model.TrendingRepoApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TrendingListViewModel : ViewModel() {

    private val trendingRepoApiService = TrendingRepoApiService()
    private val disposable = CompositeDisposable()

    val trendingRepoList = MutableLiveData<List<GithubEntity>>()
    val trendingRepoError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
                trendingRepoApiService.getRepos()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<GithubEntity>>(){
                            override fun onSuccess(repoList: List<GithubEntity>) {
                                trendingRepoList.value = repoList
                                trendingRepoError.value = false
                                loading.value = false
                            }

                            override fun onError(e: Throwable) {
                                trendingRepoError.value = true
                                loading.value = false
                                e.printStackTrace()
                            }
                        })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}

