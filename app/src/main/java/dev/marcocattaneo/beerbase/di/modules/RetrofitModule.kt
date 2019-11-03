package dev.marcocattaneo.beerbase.di.modules

import dagger.Module
import dagger.Provides
import dev.marcocattaneo.beerbase.data.GithubService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl("http://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideGithubService(retrofit: Retrofit) = retrofit.create<GithubService>(GithubService::class.java)

}