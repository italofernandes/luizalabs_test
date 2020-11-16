package br.com.luizalabs.gistvisualizer.presentation.models

sealed class GistListViewState

open class LoadingState(
    val loading: Boolean
): GistListViewState()

class SuccessState(
    loading: Boolean,
    val gistList: List<GistItem>
): LoadingState(loading)

class ErrorState(
    loading: Boolean,
    val msgRes: Int
): LoadingState(loading)




