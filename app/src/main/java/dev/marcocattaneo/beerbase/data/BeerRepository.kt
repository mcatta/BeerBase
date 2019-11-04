package dev.marcocattaneo.beerbase.data

import dev.marcocattaneo.beerbase.model.BeerModel
import javax.inject.Inject

class BeerRepository @Inject constructor(
    private val beerAPIService: BeerAPIService
) {

    suspend fun searchBeer(query: String): List<BeerModel> {
        return beerAPIService.search(query).records
    }

}