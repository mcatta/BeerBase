package dev.marcocattaneo.domain.interactors

abstract class UseCase<I, O> {

    abstract suspend fun execute(input: I): O

}