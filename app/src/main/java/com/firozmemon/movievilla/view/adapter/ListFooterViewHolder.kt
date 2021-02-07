package com.firozmemon.movievilla.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firozmemon.movievilla.R
import com.firozmemon.movievilla.util.State
import kotlinx.android.synthetic.main.rv_item_movie_footer.view.*

class ListFooterViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(status: State?) {
        itemView.progress_bar.visibility = if (status == State.LOADING) VISIBLE else View.INVISIBLE
        itemView.txt_error.visibility = if (status == State.ERROR) VISIBLE else View.INVISIBLE
    }

    companion object {
        fun create(retry: () -> Unit, parent: ViewGroup): ListFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.rv_item_movie_footer, parent, false)
            view.txt_error.setOnClickListener { retry() }
            return ListFooterViewHolder(view)
        }
    }
}