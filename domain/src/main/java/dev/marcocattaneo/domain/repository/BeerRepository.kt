package dev.marcocattaneo.domain.repository

import dev.marcocattaneo.domain.models.BeerModel

interface BeerRepository {

    suspend fun searchBeer(query: String): List<BeerModel>

}