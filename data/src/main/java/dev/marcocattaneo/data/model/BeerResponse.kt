package dev.marcocattaneo.data.model

data class BeerResponse(
    val nhits: Int,
    val records: List<BeerModel>
)