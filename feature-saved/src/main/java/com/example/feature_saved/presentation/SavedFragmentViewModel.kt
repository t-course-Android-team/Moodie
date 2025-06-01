package com.example.feature_saved.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.WatchedMoviesDataBase
import com.example.data.WatchedMoviesEntity
import com.example.data.WatchedMoviesRepoImpl
import com.example.domain.WatchedMoviesRepo
import kotlinx.coroutines.launch

class SavedFragmentViewModel(private val application: Application): ViewModel() {

    private val database = WatchedMoviesDataBase.getWatchedDataBase(application.applicationContext)
    private val repository: WatchedMoviesRepo = WatchedMoviesRepoImpl(database.watchedMoviesDao())

    private val _films = MutableLiveData<List<WatchedMoviesEntity>>()
    val films: LiveData<List<WatchedMoviesEntity>> = _films
    private val _screenState = MutableLiveData<State>(State.Content())
    val screenState: LiveData<State> = _screenState
    private var currentOffset = 0
    private var currentLimit = 30

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _screenState.value = State.Loading
            try {
                val itemsResult = repository.getPagedWatchedMovies(
                    limit = currentLimit,
                    offset = currentOffset
                )
                val totalCountResult = repository.getWatchedMoviesCount()

                if (itemsResult.isNotEmpty() && totalCountResult != 0) {
                    _films.value = itemsResult

                    _screenState.value = State.Content(
                        canLoadMore = currentOffset + currentLimit < totalCountResult,
                        canLoadPrevious = currentOffset > 0
                    )
                } else {
                    val error = Throwable("Items is empty = ${itemsResult.isEmpty()}, total count items = ${totalCountResult}")
                    handleError(error)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun loadMoreItems() {
        viewModelScope.launch {
            try {
                val totalCount = repository.getWatchedMoviesCount()
                if (totalCount < currentOffset + 3 * currentLimit / 2) {
                    currentOffset = totalCount - currentLimit
                } else {
                    currentOffset += currentLimit / 2
                }
                val result = repository.getPagedWatchedMovies(
                    limit = currentLimit,
                    offset = currentOffset
                )

                if (result.isNotEmpty()) {
                    _films.value = result
                    updateContentState()
                } else {
                    val error = Throwable("Items is empty)")
                    handleError(error)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun loadPreviousItems() {
        viewModelScope.launch {
            try {
                currentOffset = if (currentOffset < currentLimit / 2) {
                    0
                } else {
                    currentOffset - currentLimit / 2
                }
                val result = repository.getPagedWatchedMovies(
                    limit = currentLimit,
                    offset = currentOffset
                )

                if (result.isNotEmpty()) {
                    _films.value = result
                    updateContentState()
                } else {
                    val error = Throwable("Items is empty)")
                    handleError(error)
                }
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    private fun updateContentState() {
        viewModelScope.launch {
            val totalCount = repository.getWatchedMoviesCount()
            _screenState.value = State.Content(
                canLoadMore = currentOffset + currentLimit < totalCount,
                canLoadPrevious = currentOffset > 0
            )
        }
    }

    fun watchMovie(name: String) {
        viewModelScope.launch {
            repository.updateMovieIsSaved(name, true)
        }
        loadInitialData()
    }

    fun deleteMovie(name: String) {
        viewModelScope.launch {
            repository.deleteMovie(name)
        }
        loadInitialData()
    }

    private fun handleError(e: Throwable) {
        _screenState.value = State.Error(
            message = e.message ?: "Неизвестная ошибка",
            retryAction = { loadInitialData() }
        )
    }
}