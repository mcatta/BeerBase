package dev.marcocattaneo.beerbase.ui.main.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.marcocattaneo.beerbase.di.scopes.ViewModelKey
import dev.marcocattaneo.beerbase.ui.main.MainViewModel


@Module
abstract class MainActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

}