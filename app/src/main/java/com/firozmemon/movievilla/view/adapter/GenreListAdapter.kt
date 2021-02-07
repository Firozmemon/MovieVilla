package com.firozmemon.movievilla.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firozmemon.movievilla.databinding.RvItemGenreBinding
import com.firozmemon.movievilla.model.details.Genre

class GenreListAdapter(private val genreList: List<Genre>,
                       val rowClick: (Genre) -> Unit) : RecyclerView.Adapter<GenreListAdapter.GenreViewHolderHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  =
        GenreViewHolderHolder(RvItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: GenreViewHolderHolder, position: Int) = holder.bind(genreList[position])

    override fun getItemCount() = genreList.size

    inner class GenreViewHolderHolder(private val binding: RvItemGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.apply {
                txtGenre.text = genre.name
                // root.setOnClickListener { rowClick.invoke(genre) }
            }
        }
    }
}