package dev.marcocattaneo.data.interactors

import dev.marcocattaneo.data.repository.BeerRepositoryImpl
import dev.marcocattaneo.domain.interactors.UseCase
import dev.marcocattaneo.domain.models.BeerModel
import javax.inject.Inject

class SearchBeerUseCase @Inject constructor(private val beerRepository: BeerRepositoryImpl) :
    UseCase<String, List<BeerModel>>() {

    override suspend fun execute(input: String): List<BeerModel> {
        return beerRepository.searchBeer(input)
    }

}