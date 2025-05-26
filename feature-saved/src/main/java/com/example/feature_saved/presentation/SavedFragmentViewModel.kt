package com.example.feature_saved.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_saved.domain.FilmEntity
import com.example.feature_saved.domain.FilmRepository
import kotlinx.coroutines.launch

class SavedFragmentViewModel(
    private val repository: FilmRepository
): ViewModel() {

    private val _films = MutableLiveData<List<FilmEntity>>()
    val films: LiveData<List<FilmEntity>> = _films
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
                val itemsResult = repository.getFilms(currentLimit, currentOffset)
                val totalCountResult = repository.getCountFilms()

                if (itemsResult.isNotEmpty() && totalCountResult != 0) {
                    _films.value = itemsResult
                    val totalCount = totalCountResult

                    _screenState.value = State.Content(
                        canLoadMore = currentOffset + currentLimit < totalCount,
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
                val totalCount = repository.getCountFilms()
                if (totalCount < currentOffset + 3 * currentLimit / 2) {
                    currentOffset = totalCount - currentLimit
                } else {
                    currentOffset += currentLimit / 2
                }
                val result = repository.getFilms(currentOffset, currentLimit)

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
                val result = repository.getFilms(currentOffset, currentLimit)

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
            val totalCount = repository.getCountFilms()
            _screenState.value = State.Content(
                canLoadMore = currentOffset + currentLimit < totalCount,
                canLoadPrevious = currentOffset > 0
            )
        }
    }

    private fun handleError(e: Throwable) {
        _screenState.value = State.Error(
            message = e.message ?: "Неизвестная ошибка",
            retryAction = { loadInitialData() }
        )
    }
}