package com.jjurisic.movielist.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

private const val imageBaseUrl = "https://image.tmdb.org/t/p/w500/"

fun ImageView.loadImage(imageUrl: String?) {
    val fullUrl = imageBaseUrl + imageUrl
    Glide.with(this).load(fullUrl).into(this)
}