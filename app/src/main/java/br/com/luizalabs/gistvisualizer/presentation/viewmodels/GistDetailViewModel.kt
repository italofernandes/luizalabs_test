package br.com.luizalabs.gistvisualizer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luizalabs.gistvisualizer.domain.usecases.FavoriteGistUseCase
import br.com.luizalabs.gistvisualizer.presentation.converters.GistItemToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.models.DetailSuccessViewState
import br.com.luizalabs.gistvisualizer.presentation.models.GistDetailViewState
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GistDetailViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val converter: GistItemToGistConverter,
    private val favoriteUseCase: FavoriteGistUseCase
): ViewModel(){

    private val _viewState by lazy { MutableLiveData<GistDetailViewState>() }

    val stateGistDetail: LiveData<GistDetailViewState>
        get() = _viewState

    private lateinit var gistItem: GistItem

    fun populate(gist: GistItem) {
        gistItem = gist
        _viewState.value = DetailSuccessViewState(gist)
    }

    fun favorite(){
        viewModelScope.launch(dispatcher) {
            gistItem.favorite = !gistItem.favorite
            favoriteUseCase.gist = converter.convert(gistItem)
            favoriteUseCase.execute()

            _viewState.postValue(DetailSuccessViewState(
                gistItem
            ))
        }
    }
}