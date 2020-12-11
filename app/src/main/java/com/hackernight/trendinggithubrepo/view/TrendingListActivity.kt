package com.hackernight.trendinggithubrepo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hackernight.trendinggithubrepo.R
import com.hackernight.trendinggithubrepo.viewmodel.TrendingListViewModel

class TrendingListActivity : AppCompatActivity() {

    private lateinit var viewModel: TrendingListViewModel
    val trendingListAdapter = TrendingListAdapter(arrayListOf())

    private lateinit var trendingGithubRepoRecyclerList : RecyclerView
    private lateinit var listError : TextView
    private lateinit var retry : Button
    private lateinit var loadingView : ProgressBar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_list)

        trendingGithubRepoRecyclerList = findViewById(R.id.trendingGithubRepoRecyclerList)
        listError = findViewById(R.id.listError)
        retry = findViewById(R.id.retry)
        loadingView = findViewById(R.id.loadingView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        viewModel = ViewModelProvider(this).get(TrendingListViewModel::class.java)
        viewModel.refresh()

        trendingGithubRepoRecyclerList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = trendingListAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        observeViewModel()

    }

    private fun observeViewModel() {

        viewModel.trendingRepoList.observe(this, Observer {
            it?.let {
                trendingGithubRepoRecyclerList.visibility = View.VISIBLE
                trendingListAdapter.updateList(it)
            }
        })

        viewModel.trendingRepoError.observe(this, Observer {
            it?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
                retry.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE

                if (it){
                    trendingGithubRepoRecyclerList.visibility = View.GONE
                    listError.visibility = View.GONE
                    retry.visibility = View.GONE
                }
            }
        })

    }

}