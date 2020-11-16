package br.com.luizalabs.gistvisualizer.commons

interface BaseConverter<T, E> {
     fun convert(from: T): E
}
