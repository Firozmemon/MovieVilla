package com.firozmemon.movievilla.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firozmemon.movievilla.R
import com.firozmemon.movievilla.view.adapter.GenreListAdapter
import com.firozmemon.movievilla.model.details.Genre
import com.firozmemon.movievilla.util.KEY_MOVIE_ID
import com.firozmemon.movievilla.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.activity_movie_details.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieDetailsActivity : AppCompatActivity() {

    private val viewModel: MovieDetailsViewModel by viewModel<MovieDetailsViewModel>()
    private lateinit var genreListAdapter: GenreListAdapter

    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        movieId = intent.getIntExtra(KEY_MOVIE_ID, 0)
        if (movieId == 0) {
            finish()
        }

        img_movie_back.setOnClickListener { finish() }
        img_movie_bookmark.setOnClickListener {
            if (viewModel.ldIsFavourite.value == false) viewModel.addToFavourite(viewModel.ldMovieDetails.value!!)
            else viewModel.removeFromFavourite(viewModel.ldMovieDetails.value!!)
        }

        viewModel.ldIsFavourite.observe(this, {
            img_movie_bookmark.background =
                ContextCompat.getDrawable(
                    this,
                    if (it) R.drawable.ic_bookmark_filled else R.drawable.ic_bookmark_empty
                )
        })
        viewModel.ldMovieDetails.observe(this, {
            Glide.with(img_movie_banner.context)
                .load("https://image.tmdb.org/t/p/w500/${it.poster_path}")
                .into(img_movie_banner)
            txt_movie_name.text = it.title
            txt_movie_tagline.text = it.tagline
            txt_movie_release_date.text = it.release_date
            txt_movie_overview_content.text = it.overview
            it.genres?.let { it1 -> initAdapter(it1) }
        })
        viewModel.getMovieDetails(movieId)
    }

    private fun initAdapter(genres: List<Genre>) {
        genreListAdapter = GenreListAdapter(genres) { /* no-op */ }
        recycler_view_genre.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )
        recycler_view_genre.adapter = genreListAdapter
    }

}