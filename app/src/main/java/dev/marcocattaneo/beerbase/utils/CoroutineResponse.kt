package dev.marcocattaneo.beerbase.utils

sealed class CoroutineResponse

data class Error(val error: Throwable): CoroutineResponse()

data class Success<T>(val result: T): CoroutineResponse()