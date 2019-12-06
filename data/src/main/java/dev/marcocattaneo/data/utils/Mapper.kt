package dev.marcocattaneo.data.utils

interface Mapper<in T, R> {

    fun map(t: T) : R

}