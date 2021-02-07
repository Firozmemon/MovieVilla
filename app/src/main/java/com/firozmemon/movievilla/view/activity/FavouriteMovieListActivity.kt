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
import com.firozmemon.movievilla.util.KEY_MOVIE_ID
import com.firozmemon.movievilla.viewmodel.FavouriteMoviesViewModel
import kotlinx.android.synthetic.main.activity_favourite_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class FavouriteMovieListActivity : AppCompatActivity() {

    private val viewModel: FavouriteMoviesViewModel by viewModel<FavouriteMoviesViewModel>()
    private lateinit var favouriteListAdapter: FavouriteListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_movie)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.ldFavMovies.observe(this, {
            if (it.isNullOrEmpty().not()) {
                recycler_view.visibility = View.VISIBLE
                txt_error.visibility = View.GONE
                favouriteListAdapter = FavouriteListAdapter(it) { movieResult ->
                    startActivity(
                        Intent(
                            this@FavouriteMovieListActivity,
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
                txt_error.visibility = View.VISIBLE
            }
        })

        viewModel.getAllFavouriteMovies()
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