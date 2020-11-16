package br.com.luizalabs.gistvisualizer.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.luizalabs.gistvisualizer.R
import br.com.luizalabs.gistvisualizer.domain.exception.GistServiceCommunicationException
import br.com.luizalabs.gistvisualizer.domain.exception.NoGistDataException
import br.com.luizalabs.gistvisualizer.domain.usecases.FavoriteGistUseCase
import br.com.luizalabs.gistvisualizer.domain.usecases.GetAllGistsUseCase
import br.com.luizalabs.gistvisualizer.domain.usecases.SearchGistsByNameUserCase
import br.com.luizalabs.gistvisualizer.presentation.converters.GistItemToGistConverter
import br.com.luizalabs.gistvisualizer.presentation.converters.ListGistInListGistItemConverter
import br.com.luizalabs.gistvisualizer.presentation.models.*
import kotlinx.coroutines.*

class GistListViewModel(
    private val dispatcher: CoroutineDispatcher,
    private val listGistItemConverter: ListGistInListGistItemConverter,
    private val gistConverter: GistItemToGistConverter,
    private val getAllGistsUseCase: GetAllGistsUseCase,
    private val addGistToFavoritesUseCase: FavoriteGistUseCase,
    private val searchGistsByNameUserCase: SearchGistsByNameUserCase
) : ViewModel() {

    private val _viewState: MutableLiveData<GistListViewState> by lazy { MutableLiveData<GistListViewState>() }
    val stateGistList: LiveData<GistListViewState>
        get() = _viewState

    private var currentPage: Int = 1
    private var currentJob: Job? = null
    private val gistItemList = mutableListOf<GistItem>()

    fun getGists(reset: Boolean = false) {

        takeIf { reset }?.apply {
            currentPage = 1
            gistItemList.clear()
        }

        val completed = currentJob?.isCompleted ?: true

        if(completed) {
            currentJob = viewModelScope.launch(dispatcher) {

                getAllGistsUseCase.page = currentPage
                _viewState.postValue(LoadingState(true))
                try {
                    val gists = listGistItemConverter.convert(getAllGistsUseCase.execute())
                    currentPage++

                    withContext(Dispatchers.Main) {
                        gistItemList.addAll(gists)
                        _viewState.value = SuccessState(
                            loading = false,
                            gistList = gistItemList
                        )
                    }

                } catch (e: NoGistDataException) {
                    _viewState.postValue(ErrorState(
                        loading = false,
                        msgRes = R.string.gist_no_data_error

                    ))
                } catch (e: GistServiceCommunicationException) {
                    _viewState.postValue(ErrorState(
                        loading = false,
                        msgRes = R.string.gist_conm_error
                    ))
                }
            }
        }
    }

    fun searchGistsByUserName(name: String?) {
        name?.let {ownerName ->
            viewModelScope.launch(dispatcher) {

                searchGistsByNameUserCase.userName = ownerName
                _viewState.postValue(LoadingState(true))

                try {
                    val searchList = searchGistsByNameUserCase.execute()

                    _viewState.postValue(SuccessState(
                        loading = false,
                        gistList = listGistItemConverter.convert(searchList)
                    ))
                } catch (e: NoGistDataException) {
                    _viewState.postValue(ErrorState(
                        loading = false,
                        msgRes = R.string.gist_no_data_error
                    ))
                } catch (e: GistServiceCommunicationException) {
                    _viewState.postValue(ErrorState(
                        loading = false,
                        msgRes = R.string.gist_conm_error
                    ))
                }
            }
        }
    }

    fun backToPage() {
        _viewState.value = SuccessState(
            loading = false,
            gistList = gistItemList
        )
    }

    fun addOrRemoveFavorite(gistIndex: Int) {
        viewModelScope.launch(dispatcher) {
            val favorite = gistItemList[gistIndex].favorite

            gistItemList[gistIndex].favorite = !favorite
            addGistToFavoritesUseCase.gist = gistConverter.convert(gistItemList[gistIndex])
            addGistToFavoritesUseCase.execute()
            _viewState.postValue(SuccessState(
                loading = false,
                gistList = gistItemList
            ))
        }
    }
}