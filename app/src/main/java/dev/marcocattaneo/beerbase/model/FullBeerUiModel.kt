package dev.marcocattaneo.beerbase.model

data class FullBeerUiModel(
    val id: Int, val name: String, val beerImage: String?, val description: String, val subtitle: String
)