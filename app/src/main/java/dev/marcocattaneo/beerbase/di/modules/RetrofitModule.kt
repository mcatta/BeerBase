package dev.marcocattaneo.beerbase.di.modules

import dagger.Module
import dagger.Provides
import dev.marcocattaneo.beerbase.data.BeerAPIService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl("https://data.opendatasoft.com/api/records/1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideBeerAPIService(retrofit: Retrofit) = retrofit.create<BeerAPIService>(BeerAPIService::class.java)

}