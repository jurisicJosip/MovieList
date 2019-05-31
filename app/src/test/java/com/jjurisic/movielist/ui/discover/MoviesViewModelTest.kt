package com.jjurisic.movielist.ui.discover

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jjurisic.movielist.model.Movie
import com.jjurisic.movielist.model.Movies
import com.jjurisic.movielist.model.MoviesApi
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MoviesViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var moviesApi: MoviesApi

    @InjectMocks
    var moviesViewModel = DiscoverMoviesViewModel()

    @Before
    fun initMockito() {
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setupSchedulers() {
        val immediate = object : Scheduler() {

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, false)
            }

            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun getMoviesSuccessPath() {
        val movie = Movie("id", "Movie Title", "Movie url")
        val movies = Movies(1, listOf(movie))
        Mockito.`when`(moviesApi.getMovies()).thenReturn(Single.just(movies))

        moviesViewModel.refresh()
        Assert.assertEquals(1, moviesViewModel.movies.value?.results?.size)
        Assert.assertEquals(1, moviesViewModel.movies.value?.page)
        Assert.assertEquals(false, moviesViewModel.moviesLoadingError.value)
        Assert.assertEquals(false, moviesViewModel.loading.value)
    }

    @Test
    fun getMoviesErrorPath() {
        Mockito.`when`(moviesApi.getMovies()).thenReturn(Single.error(Throwable()))

        moviesViewModel.refresh()
        Assert.assertEquals(null, moviesViewModel.movies.value)
        Assert.assertEquals(true, moviesViewModel.moviesLoadingError.value)
        Assert.assertEquals(false, moviesViewModel.loading.value)
    }
}