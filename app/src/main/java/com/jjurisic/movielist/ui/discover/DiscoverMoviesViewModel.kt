package com.jjurisic.movielist.ui.discover

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jjurisic.movielist.di.DaggerApiComponent
import com.jjurisic.movielist.model.Movies
import com.jjurisic.movielist.model.MoviesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DiscoverMoviesViewModel : ViewModel() {

    @Inject
    lateinit var moviesApi: MoviesApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val compositeDisposable = CompositeDisposable()

    var movies = MutableLiveData<Movies>()
    val loading = MutableLiveData<Boolean>()
    val moviesLoadingError = MutableLiveData<Boolean>()

    fun refresh() {
        fetchData()
    }

    private fun fetchData() {
        loading.value = true
        compositeDisposable.add(
            moviesApi.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Movies>() {
                    override fun onSuccess(data: Movies) {
                        loading.value = false
                        movies.value = data
                        moviesLoadingError.value = false
                    }

                    override fun onError(e: Throwable) {
                        loading.value = false
                        moviesLoadingError.value = true
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}