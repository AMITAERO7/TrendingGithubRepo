package com.hackernight.trendinggithubrepo.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hackernight.trendinggithubrepo.model.GithubEntity
import com.hackernight.trendinggithubrepo.model.IGithubEntityDao
import com.hackernight.trendinggithubrepo.model.TrendingRepoApiService
import com.hackernight.trendinggithubrepo.model.TrendingRepoDatabase
import com.hackernight.trendinggithubrepo.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class TrendingListViewModel(application: Application) : BaseViewModel(application) {

    private val preferenceHelper = SharedPreferenceHelper(getApplication())
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L

    private val trendingRepoApiService = TrendingRepoApiService()
    private val disposable = CompositeDisposable()

    val trendingRepoList = MutableLiveData<List<GithubEntity>>()
    val trendingRepoError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val  updateTime = preferenceHelper.getUpdateTime()

        if (updateTime!=null && updateTime != 0L && System.nanoTime()-updateTime < refreshTime){
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }

    }

    fun refreshByPassCache(){
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val repoList = TrendingRepoDatabase(getApplication()).githubEntity().getAllGithubEntity()
            repoGithubRetrieved(repoList)
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
                trendingRepoApiService.getRepos()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<List<GithubEntity>>(){
                            override fun onSuccess(repoList: List<GithubEntity>) {
                              storeGithubEntityLocally(repoList)
                            }

                            override fun onError(e: Throwable) {
                                trendingRepoError.value = true
                                loading.value = false
                                e.printStackTrace()
                                Log.d("Loading Error", e.printStackTrace().toString())
                            }
                        })
        )
    }

    private fun storeGithubEntityLocally(list: List<GithubEntity>){
        launch {
            val dao : IGithubEntityDao = TrendingRepoDatabase(getApplication()).githubEntity()
            dao.deleteGithubEntity()

            dao.insertAll(*list.toTypedArray())

            repoGithubRetrieved(list)
        }
        //save time to sharedpreference
        preferenceHelper.saveUpdateTime(System.nanoTime())
    }

    private fun repoGithubRetrieved(repoList:List<GithubEntity>){
        trendingRepoList.value = repoList
        trendingRepoError.value = false
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}

