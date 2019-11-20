package dev.marcocattaneo.data.utils

class CoroutineResponse<T> private constructor(val result: T? = null, val error: Throwable? = null) {
    
    companion object {
        fun <T> result(result: T) = CoroutineResponse(result)
        fun <T> error(error: Throwable) = CoroutineResponse<T>(error = error)
    }
    
}