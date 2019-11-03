package dev.marcocattaneo.beerbase.di.modules

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dev.marcocattaneo.beerbase.utils.DaggerViewModelFactory

@Module
abstract class DaggerViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: DaggerViewModelFactory): ViewModelProvider.Factory

}