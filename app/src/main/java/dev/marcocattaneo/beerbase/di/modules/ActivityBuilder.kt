package dev.marcocattaneo.beerbase.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.marcocattaneo.beerbase.ui.detail.DetailActivity
import dev.marcocattaneo.beerbase.ui.detail.di.DetailActivityModule

import dev.marcocattaneo.beerbase.ui.main.MainActivity

import dev.marcocattaneo.beerbase.ui.main.di.MainActivityModule


@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun bindMainActivity(): MainActivity?

    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    internal abstract fun bindDetailActivity(): DetailActivity?
}