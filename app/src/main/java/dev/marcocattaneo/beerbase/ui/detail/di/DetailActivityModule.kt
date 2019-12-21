package dev.marcocattaneo.beerbase.ui.detail.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.marcocattaneo.beerbase.di.scopes.ViewModelKey
import dev.marcocattaneo.beerbase.ui.detail.DetailViewModel

@Module
abstract class DetailActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel(detailViewModel: DetailViewModel) : ViewModel

}