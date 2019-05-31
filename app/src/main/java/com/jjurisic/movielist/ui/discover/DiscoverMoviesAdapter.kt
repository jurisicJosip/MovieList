package com.jjurisic.movielist.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jjurisic.movielist.R
import com.jjurisic.movielist.model.Movie

class DiscoverMoviesAdapter(var movies: ArrayList<Movie>) : RecyclerView.Adapter<DiscoverMoviesViewHolder>() {

    fun setMovies(movies: List<Movie>) {
        this.movies.clear()
        this.movies.addAll(movies)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMoviesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_movie, parent, false)
        return DiscoverMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiscoverMoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}