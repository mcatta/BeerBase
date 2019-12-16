package dev.marcocattaneo.data.repository

import dev.marcocattaneo.domain.models.BeerModel
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerAPIService {

    @GET("beers")
    suspend fun search(@Query("beer_name") beer: String) : List<BeerModel>

    @GET("beers")
    suspend fun getByIds(@Query("ids") ids: Array<String>) : List<BeerModel>

}