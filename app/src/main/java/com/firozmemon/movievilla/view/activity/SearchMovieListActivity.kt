package com.firozmemon.movievilla.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firozmemon.movievilla.R
import com.firozmemon.movievilla.view.adapter.FavouriteListAdapter
import com.firozmemon.movievilla.util.EMPTY_STRING
import com.firozmemon.movievilla.util.KEY_MOVIE_ID
import com.firozmemon.movievilla.util.KEY_MOVIE_QUERY
import com.firozmemon.movievilla.viewmodel.SearchMoviesViewModel
import kotlinx.android.synthetic.main.activity_discover_movie.*
import kotlinx.android.synthetic.main.activity_favourite_movie.*
import kotlinx.android.synthetic.main.activity_favourite_movie.recycler_view
import kotlinx.android.synthetic.main.activity_favourite_movie.txt_error
import org.koin.android.viewmodel.ext.android.viewModel

class SearchMovieListActivity : AppCompatActivity() {

    private val viewModel: SearchMoviesViewModel by viewModel<SearchMoviesViewModel>()
    private lateinit var favouriteListAdapter: FavouriteListAdapter
    private var movieQuery: String = EMPTY_STRING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieQuery = intent.getStringExtra(KEY_MOVIE_QUERY) ?: EMPTY_STRING
        if (movieQuery.isNullOrBlank()) {
            finish()
        }

        viewModel.ldSearchMovies.observe(this, {
            if (it.isNullOrEmpty().not()) {
                recycler_view.visibility = View.VISIBLE
                txt_error.visibility = View.GONE
                favouriteListAdapter = FavouriteListAdapter(it) { movieResult ->
                    startActivity(
                        Intent(
                            this@SearchMovieListActivity,
                            MovieDetailsActivity::class.java
                        ).apply {
                            putExtra(KEY_MOVIE_ID, movieResult.id)
                        })
                }
                recycler_view.layoutManager = GridLayoutManager(
                    this, 3,
                    RecyclerView.VERTICAL,
                    false
                )
                recycler_view.adapter = favouriteListAdapter
            } else {
                recycler_view.visibility = View.GONE
                txt_error.text = "No Data Found"
                txt_error.visibility = View.VISIBLE
            }
        })

        viewModel.searchMovies(movieQuery)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(menuItem)
    }

}