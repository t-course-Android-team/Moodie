package com.example.feature_response.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_response.domain.FilmEntity
import com.example.feature_response.domain.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ResponseViewModel(
    private val repository: FilmRepository
)  : ViewModel() {

    private val _films = MutableLiveData<List<FilmEntity>>()
    val films: LiveData<List<FilmEntity>> = _films

    private val _loadProgress = MutableLiveData<Int>(0)
    val loadProgress: LiveData<Int> = _loadProgress

    private var _state = MutableStateFlow<State>(State.START)
    val state: StateFlow<State> = _state.asStateFlow()

    fun getFilms(query: String?) {
        if(_state.value == State.START) {
            _films.value = listOf()
            viewModelScope.launch {
                flow {
                    emit(State.LOADING)
                    if (query == null) {
                        emit(State.ERROR)
                    } else {
                        queryToTitlesList(query).forEach {
                            val film = repository.getFilm(it)
                            withContext(Dispatchers.Main) {
                                addFilm(film)
                                _loadProgress.value = _loadProgress.value?.plus(1)
                            }
                        }
                    }
                    emit(State.OK)
                }
                    .flowOn(Dispatchers.IO)
                    .catch { emit(State.ERROR) }
                    .collect(_state)
            }
        }
    }

    private fun addFilm(film: FilmEntity?) {
        val list = mutableListOf<FilmEntity>()
        _films.value?.let { list.addAll(it) }
        if (film != null) list.add(film)
        _films.value = list
    }

    fun setDataToSave(films: List<FilmEntity>) {
        _films.value = films
    }

    fun refresh() {
        viewModelScope.launch {
            _state.emit(State.START)
            _loadProgress.value = 0
        }
    }

    private fun queryToTitlesList(query: String): List<String> {
        return query.split("*")
    }

}