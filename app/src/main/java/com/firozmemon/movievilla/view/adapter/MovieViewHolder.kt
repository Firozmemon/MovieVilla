package com.firozmemon.movievilla.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firozmemon.movievilla.R
import com.firozmemon.movievilla.model.MovieResult
import kotlinx.android.synthetic.main.rv_item_movie.view.*

class MovieViewHolder(private val performClick: (MovieResult) -> Unit, view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movieResult: MovieResult?) {
        if (movieResult != null) {
            itemView.txt_movie_name.text = movieResult.title
            Glide.with(itemView.img_movie_banner.context).load("https://image.tmdb.org/t/p/original/${movieResult.poster_path}").into(itemView.img_movie_banner)

            itemView.card_view.setOnClickListener { performClick(movieResult) }
        }
    }

    companion object {
        fun create(performClick: (MovieResult) -> Unit, parent: ViewGroup): MovieViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item_movie, parent, false)
            return MovieViewHolder(performClick, view)
        }
    }
}