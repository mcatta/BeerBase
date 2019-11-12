package dev.marcocattaneo.beerbase.di.modules

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class CoroutineModule {

    @Provides
    fun coroutineDispatcher() = Dispatchers.Default

}