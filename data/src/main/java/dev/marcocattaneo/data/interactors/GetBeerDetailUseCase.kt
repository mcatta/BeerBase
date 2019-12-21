package dev.marcocattaneo.data.interactors

import dev.marcocattaneo.data.repository.BeerRepositoryImpl
import dev.marcocattaneo.domain.interactors.UseCase
import dev.marcocattaneo.domain.models.BeerModel
import javax.inject.Inject

class GetBeerDetailUseCase @Inject constructor(
    private val beerRepositoryImpl: BeerRepositoryImpl
) : UseCase<Int, BeerModel?>() {

    override suspend fun execute(input: Int): BeerModel? {
        return this.beerRepositoryImpl.getBeersByIds(arrayOf(input)).firstOrNull()
    }

}