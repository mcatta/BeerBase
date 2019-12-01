package dev.marcocattaneo.data.model

import dev.marcocattaneo.domain.models.BeerModel

data class BeerResponse(
    val nhits: Int,
    val records: List<BeerModel>
)