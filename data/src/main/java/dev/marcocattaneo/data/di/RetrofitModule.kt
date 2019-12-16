package dev.marcocattaneo.data.di

import dagger.Module
import dagger.Provides
import dev.marcocattaneo.data.BuildConfig
import dev.marcocattaneo.data.repository.BeerAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val client = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            this.addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
        }
    }.build()

    @Provides
    @Singleton
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.punkapi.com/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(this.client)
        .build()


    @Provides
    @Singleton
    internal fun provideBeerAPIService(retrofit: Retrofit) =
        retrofit.create<BeerAPIService>(BeerAPIService::class.java)

}