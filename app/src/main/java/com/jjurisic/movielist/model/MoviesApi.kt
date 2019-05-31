package com.jjurisic.movielist.model

import io.reactivex.Single
import retrofit2.http.GET

interface MoviesApi {

    @GET("discover/movie")
    fun getMovies(): Single<Movies>
}