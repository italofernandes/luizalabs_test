package br.com.luizalabs.gistvisualizer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.domain.exception.NoGistDataException
import br.com.luizalabs.gistvisualizer.domain.usecases.FavoriteGistUseCase
import br.com.luizalabs.gistvisualizer.domain.usecases.GetAllFavoriteGistsUseCase
import br.com.luizalabs.gistvisualizer.presentation.converters.GistItemToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.ListGistInListGistItemConverter
import br.com.luizalabs.gistvisualizer.presentation.models.ErrorState
import br.com.luizalabs.gistvisualizer.presentation.models.GistItem
import br.com.luizalabs.gistvisualizer.presentation.models.GistListViewState
import br.com.luizalabs.gistvisualizer.presentation.models.SuccessState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class FavoriteGistsViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val addGistToFavoritesUseCase: FavoriteGistUseCase,
    private val getAllFavoriteGistsUseCase: GetAllFavoriteGistsUseCase,
    private val listGistItemConverter: ListGistInListGistItemConverter,
    private val gistConverter: GistItemToGistConverter,
    ) : ViewModel() {

    private val _viewState: MutableLiveData<GistListViewState> by lazy { MutableLiveData<GistListViewState>() }
    val stateGistList: LiveData<GistListViewState>
        get() = _viewState

    private var gistItemList = mutableListOf<GistItem>()

    fun showFavorites() {
        viewModelScope.launch(dispatcher) {
            try {
                gistItemList.addAll(listGistItemConverter.convert(getAllFavoriteGistsUseCase.execute()))
                _viewState.postValue(
                    SuccessState(
                    loading = false,
                    gistList = gistItemList
                )
                )
            } catch (e: NoGistDataException) {
                _viewState.postValue(
                    ErrorState(
                    loading = false,
                    msgRes = R.string.gist_no_data_error)
                )
            }
        }
    }

    fun removeFavorite(gistIndex: Int) {
        viewModelScope.launch(dispatcher) {
            val favorite = gistItemList[gistIndex].favorite

            gistItemList[gistIndex].favorite = !favorite
            addGistToFavoritesUseCase.gist = gistConverter.convert(gistItemList[gistIndex])
            addGistToFavoritesUseCase.execute()

            gistItemList.removeAt(gistIndex)

            _viewState.postValue(SuccessState(
                loading = false,
                gistList = gistItemList
            ))
        }
    }
}