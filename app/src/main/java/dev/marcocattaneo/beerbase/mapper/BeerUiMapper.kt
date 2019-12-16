package dev.marcocattaneo.beerbase.mapper

import dev.marcocattaneo.beerbase.model.BeerUiModel
import dev.marcocattaneo.data.utils.Mapper
import dev.marcocattaneo.domain.models.BeerModel
import javax.inject.Inject

class BeerUiMapper @Inject constructor(): Mapper<BeerModel, BeerUiModel> {
    override fun map(t: BeerModel) = BeerUiModel(
        id = t.id,
        name = t.name
    )
}