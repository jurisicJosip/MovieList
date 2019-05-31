package com.jjurisic.movielist.di

import com.jjurisic.movielist.ui.discover.DiscoverMoviesViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: DiscoverMoviesViewModel)
}