package dev.marcocattaneo.beerbase.utils

data class LiveDataResult<T>(
    val status: LiveDataResultStatus,
    val data: T? = null,
    val err: Throwable? = null
) {
    companion object {
        fun <T> success(data: T) = LiveDataResult<T>(LiveDataResultStatus.SUCCESS, data)

        fun <T> loading() = LiveDataResult<T>(LiveDataResultStatus.LOADING)

        fun <T> error(err: Throwable) = LiveDataResult<T>(LiveDataResultStatus.ERROR, null, err)
    }
}