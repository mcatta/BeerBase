package dev.marcocattaneo.beerbase.data

import dev.marcocattaneo.beerbase.model.BeerModel
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}/repos")
    suspend fun fetchRepositories(@Path("user") user: String): List<BeerModel>

}