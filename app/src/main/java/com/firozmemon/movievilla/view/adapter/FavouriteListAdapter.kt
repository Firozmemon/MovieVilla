package com.firozmemon.movievilla.view.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firozmemon.movievilla.model.MovieResult

class FavouriteListAdapter(private val movieResultList: List<MovieResult>,
                           private val performClick: (MovieResult) -> Unit) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(performClick, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bind(movieResultList[position])

    override fun getItemCount() = movieResultList.size

}