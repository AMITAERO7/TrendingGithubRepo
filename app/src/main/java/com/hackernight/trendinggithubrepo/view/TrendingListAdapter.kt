package com.hackernight.trendinggithubrepo.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hackernight.trendinggithubrepo.R
import com.hackernight.trendinggithubrepo.model.GithubEntity

class TrendingListAdapter(val trendingList : ArrayList<GithubEntity>) : RecyclerView.Adapter<TrendingListAdapter.TrendingListViewHolder>() {

    fun updateList(newTrendingList:List<GithubEntity>){
        trendingList.clear()
        trendingList.addAll(newTrendingList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingListViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_trending_repo,parent,false)
        return TrendingListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: TrendingListViewHolder, position: Int) {
        holder.author.text = trendingList[position].author
        holder.name.text = trendingList[position].name
        holder.description.text = trendingList[position].description
        holder.language.text = trendingList[position].language
        holder.languageColor.text = trendingList[position].languageColor
        holder.stars.text = trendingList[position].stars
    }

    class TrendingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val author = itemView.findViewById<TextView>(R.id.author)
        val name = itemView.findViewById<TextView>(R.id.name)
        val description = itemView.findViewById<TextView>(R.id.description)
        val language = itemView.findViewById<TextView>(R.id.language)
        val languageColor = itemView.findViewById<TextView>(R.id.languageColor)
        val stars = itemView.findViewById<TextView>(R.id.stars)
    }

}