package com.jjurisic.movielist.ui.discover

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.jjurisic.movielist.model.Movie
import com.jjurisic.movielist.utils.loadImage
import kotlinx.android.synthetic.main.list_item_movie.view.*

class DiscoverMoviesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(movie: Movie?) {
        itemView.title.text = movie?.title
        itemView.image.loadImage(movie?.imageUrl)
    }
}