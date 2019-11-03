package dev.marcocattaneo.beerbase.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.marcocattaneo.beerbase.di.scopes.ActivityScope
import dev.marcocattaneo.beerbase.ui.main.MainActivity
import dev.marcocattaneo.beerbase.ui.main.di.MainActivityModule

//@Module
abstract class ActivitiesModule {

//    @ActivityScope
  //  @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun contributeActivityAndroidInjector(): MainActivity

}