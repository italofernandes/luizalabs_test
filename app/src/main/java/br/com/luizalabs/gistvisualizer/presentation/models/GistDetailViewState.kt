package br.com.luizalabs.gistvisualizer.presentation.models

sealed class GistDetailViewState

class DetailSuccessViewState(
    val gist: GistItem
): GistDetailViewState()

class DetailErrorViewState(
    val msgRes: Int
): GistDetailViewState()

