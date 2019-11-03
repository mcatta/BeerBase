package dev.marcocattaneo.beerbase

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.marcocattaneo.beerbase.di.DaggerApplicationComponent

class BeerApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build().apply {
                inject(this@BeerApplication)
            }
    }

}