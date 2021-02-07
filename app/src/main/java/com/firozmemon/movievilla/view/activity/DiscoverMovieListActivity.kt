package com.firozmemon.movievilla.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firozmemon.movievilla.R
import com.firozmemon.movievilla.view.adapter.MovieListAdapter
import com.firozmemon.movievilla.util.KEY_MOVIE_ID
import com.firozmemon.movievilla.util.KEY_MOVIE_QUERY
import com.firozmemon.movievilla.util.State
import com.firozmemon.movievilla.viewmodel.DiscoverMovieListViewModel
import kotlinx.android.synthetic.main.activity_discover_movie.*
import org.koin.android.viewmodel.ext.android.viewModel


class DiscoverMovieListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: DiscoverMovieListViewModel by viewModel<DiscoverMovieListViewModel>()
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_movie)

        initAdapter()
        initState()
    }

    private fun initAdapter() {
        movieListAdapter = MovieListAdapter({ movieResult ->
            startActivity(
                Intent(
                    this@DiscoverMovieListActivity,
                    MovieDetailsActivity::class.java
                ).apply {
                    putExtra(KEY_MOVIE_ID, movieResult.id)
                })
        }, { viewModel.retry() })
        recycler_view.layoutManager = GridLayoutManager(this, 3, RecyclerView.VERTICAL, false)
        recycler_view.adapter = movieListAdapter
        viewModel.movieResultList.observe(this, Observer {
            movieListAdapter.submitList(it)
        })
    }

    private fun initState() {
        txt_error.setOnClickListener { viewModel.retry() }
        viewModel.getState().observe(this, Observer { state ->
            progress_bar.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            txt_error.visibility =
                if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                movieListAdapter.setState(state ?: State.DONE)
            }
        })
    }

    ///////////////////////////////////////////////////////////////////////////
    // Menu Options
    ///////////////////////////////////////////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_discover, menu)

        val searchItem: MenuItem = menu!!.findItem(R.id.menu_search)
        val searchView: SearchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search Movies"
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = false

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search -> {

            }
            R.id.menu_favourite -> {
                startActivity(Intent(this, FavouriteMovieListActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query.isNullOrBlank().not()) {
            startActivity(Intent(this, SearchMovieListActivity::class.java).apply {
                putExtra(KEY_MOVIE_QUERY, query)
            })
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}