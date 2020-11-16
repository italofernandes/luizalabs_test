package br.com.luizalabs.gistvisualizer.domain.usecases

abstract class UseCaseWithParams<T>: UseCase<T>() {
    abstract fun configures(vararg args: Any)
}