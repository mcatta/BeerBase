package dev.marcocattaneo.beerbase.model

data class BeerResponse(
    val nhits: Int,
    val records: List<BeerModel>
)