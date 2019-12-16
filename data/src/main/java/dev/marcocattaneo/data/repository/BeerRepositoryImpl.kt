package dev.marcocattaneo.data.repository

import dev.marcocattaneo.domain.models.BeerModel
import dev.marcocattaneo.domain.repository.BeerRepository
import javax.inject.Inject

class BeerRepositoryImpl @Inject constructor(
    private val beerAPIService: BeerAPIService
) : BeerRepository {

    override suspend fun searchBeer(query: String): List<BeerModel> {
        return beerAPIService.search(query)
    }

    override suspend fun getBeersByIds(ids: Array<String>): List<BeerModel> {
        return beerAPIService.getByIds(ids)
    }

}