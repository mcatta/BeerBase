package dev.marcocattaneo.beerbase.di

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import dev.marcocattaneo.beerbase.BeerApplication
import dev.marcocattaneo.beerbase.di.modules.ActivityBuilder
import dev.marcocattaneo.beerbase.di.modules.DaggerViewModelFactoryModule
import dev.marcocattaneo.data.di.RetrofitModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivityBuilder::class,
    DaggerViewModelFactoryModule::class,
    RetrofitModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: BeerApplication): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(instance: DaggerApplication)

    fun inject(application: BeerApplication)

}