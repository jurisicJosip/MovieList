package com.jjurisic.movielist.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jjurisic.movielist.R
import com.jjurisic.movielist.base.BaseFragment
import com.jjurisic.movielist.utils.RecyclerViewItemDecoration
import kotlinx.android.synthetic.main.fragment_movies.*

class DiscoverMoviesFragment : BaseFragment() {

    private lateinit var moviesViewModel: DiscoverMoviesViewModel
    private val moviesAdapter = DiscoverMoviesAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moviesViewModel = ViewModelProviders.of(this).get(DiscoverMoviesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesViewModel.refresh()

        moviesListView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = moviesAdapter
            addItemDecoration(RecyclerViewItemDecoration(padding = 8))
        }

        swipeRefreshLayout.setOnRefreshListener {
            moviesViewModel.refresh()
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        moviesViewModel.movies.observe(this, Observer { movies ->
            movies?.let {
                moviesListView.visibility = View.VISIBLE
                moviesAdapter.setMovies(it.results)
                errorText.visibility = View.GONE
            }
        })

        moviesViewModel.moviesLoadingError.observe(this, Observer { isError ->
            isError?.let {
                errorText.visibility = if (it) View.VISIBLE else View.GONE
                moviesListView.visibility = if (it) View.GONE else View.VISIBLE
            }
        })

        moviesViewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let { swipeRefreshLayout.isRefreshing = it }
        })
    }
}