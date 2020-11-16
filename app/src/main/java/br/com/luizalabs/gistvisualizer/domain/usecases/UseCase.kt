package br.com.luizalabs.gistvisualizer.domain.usecases

abstract class UseCase<T> {
    abstract suspend fun execute() : T
}
