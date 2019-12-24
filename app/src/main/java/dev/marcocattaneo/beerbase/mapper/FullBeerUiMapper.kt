package dev.marcocattaneo.beerbase.mapper

import dev.marcocattaneo.beerbase.model.FullBeerUiModel
import dev.marcocattaneo.data.utils.Mapper
import dev.marcocattaneo.domain.models.BeerModel
import javax.inject.Inject

class FullBeerUiMapper @Inject constructor(): Mapper<BeerModel, FullBeerUiModel> {

    override fun map(t: BeerModel) = FullBeerUiModel(
        id = t.id,
        name = t.name,
        beerImage = t.image_url,
        description = t.description,
        subtitle = t.tagline
    )

}