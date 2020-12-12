package com.hackernight.trendinggithubrepo.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
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
            viewModel.refreshByPassCache()
            swipeRefreshLayout.isRefreshing = false
        }

        retry.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                viewModel.refreshByPassCache()
            }
        })

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        var menuItem  = menu!!.findItem(R.id.action_search)
        var searchView : SearchView = menuItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                trendingListAdapter?.filter?.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search ){
            viewModel.refresh()
        }
        return super.onOptionsItemSelected(item)
    }


}