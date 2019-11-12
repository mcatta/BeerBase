package dev.marcocattaneo.data.repository

import dev.marcocattaneo.data.model.BeerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BeerAPIService {

    @GET("search/?dataset=open-beer-database%40public-us&facet=style_name&facet=cat_name&facet=name_breweries&facet=country")
    suspend fun search(@Query("q") beer: String) : BeerResponse

}