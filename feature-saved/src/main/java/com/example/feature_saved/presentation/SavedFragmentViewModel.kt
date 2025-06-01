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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedFragmentViewModel(private val application: Application): ViewModel() {

    private val database = WatchedMoviesDataBase.getWatchedDataBase(application.applicationContext)
    private val repository: WatchedMoviesRepo = WatchedMoviesRepoImpl(database.watchedMoviesDao())

    private val _films = MutableLiveData<List<WatchedMoviesEntity>>()
    val films: LiveData<List<WatchedMoviesEntity>> = _films

    private val _count = MutableLiveData<Int>(0)
    val count: LiveData<Int> = _count
    val savedCount = MutableStateFlow(0)

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
                val itemsResult = repository.getPagedSavedMovies(
                    limit = currentLimit,
                    offset = currentOffset
                )
                val totalCountResult = repository.getSavedMoviesCount()

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
                val totalCount = repository.getSavedMoviesCount()
                if (totalCount < currentOffset + 3 * currentLimit / 2) {
                    currentOffset = totalCount - currentLimit
                } else {
                    currentOffset += currentLimit / 2
                }
                val result = repository.getPagedSavedMovies(
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
                val result = repository.getPagedSavedMovies(
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
            val totalCount = repository.getSavedMoviesCount()
            _screenState.value = State.Content(
                canLoadMore = currentOffset + currentLimit < totalCount,
                canLoadPrevious = currentOffset > 0
            )
        }
    }

    fun watchMovie(name: String, action: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.updateMovieIsSaved(name, false)
            }
        }
        val number = _films.value!!.indexOf(_films.value!!.find { it.name == name })
        val filmsTemp = _films.value!!.toMutableList()
        filmsTemp.removeAt(number)
        _films.value = filmsTemp
        action()
    }

    fun deleteMovie(name: String, action: () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteMovie(name)
            }
        }
        val number = _films.value!!.indexOf(_films.value!!.find { it.name == name })
        val filmsTemp = _films.value!!.toMutableList()
        filmsTemp.removeAt(number)
        _films.value = filmsTemp
        action()
    }

    private fun handleError(e: Throwable) {
        _screenState.value = State.Error(
            message = e.message ?: "Неизвестная ошибка",
            retryAction = { loadInitialData() }
        )
    }

    fun getSavedCount() {
        viewModelScope.launch {
            savedCount.value = repository.getSavedMoviesCount()
        }
    }

    fun setCount(number: Int) {
        _count.value = number
    }
}