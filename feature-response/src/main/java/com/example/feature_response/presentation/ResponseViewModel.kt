package com.example.feature_response.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.WatchedMoviesRepoImpl
import com.example.domain.WatchedMoviesRepo
import com.example.feature_response.data.database.WatchedMoviesMapper
import com.example.feature_response.domain.FilmEntity
import com.example.feature_response.domain.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class ResponseViewModel @Inject constructor(
    private val repository: FilmRepository,
    private val localRepository: WatchedMoviesRepo
)  : ViewModel() {

    private val _films = MutableLiveData<List<FilmEntity>>()
    val films: LiveData<List<FilmEntity>> = _films

    private val _loadProgress = MutableLiveData<Int>(0)
    val loadProgress: LiveData<Int> = _loadProgress

    private val _buttonSeenIsEnabled = MutableLiveData<Boolean>(true)
    val buttonSeenIsEnabled: LiveData<Boolean> = _buttonSeenIsEnabled

    private val _buttonSaveIsEnabled = MutableLiveData<Boolean>(true)
    val buttonSaveIsEnabled: LiveData<Boolean> = _buttonSaveIsEnabled

    private var _state = MutableStateFlow<State>(State.START)
    val state: StateFlow<State> = _state.asStateFlow()

    fun getFilms(query: String?) {
        if(_state.value == State.START) {
            _films.value = listOf()
            viewModelScope.launch {
                flow {
                    emit(State.LOADING)
                    if (query == null || query == "null") {
                        throw IllegalStateException("no movies found")
                    } else {
                        if (queryToTitlesList(query).isEmpty())
                            throw IllegalStateException("no movies found")
                        queryToTitlesList(query).forEach {
                            val film = repository.getFilm(it)
                            withContext(Dispatchers.Main) {
                                addFilm(film)
                                _loadProgress.value = _loadProgress.value?.plus(1)
                            }
                        }
                    }
                    if (_loadProgress.value == 0) throw IllegalStateException("no movies found")
                    emit(State.OK)
                }
                    .flowOn(Dispatchers.IO)
                    .catch { _state.emit(State.ERROR) }
                    .collect(_state)
            }
        }
    }

    fun saveButtonsConditions(seenIsEnabled: Boolean, savedIsEnabled: Boolean) {
        _buttonSaveIsEnabled.value = savedIsEnabled
        _buttonSeenIsEnabled.value = seenIsEnabled
    }

    fun getButtonsConditions(): List<Boolean> {
        return listOf(_buttonSeenIsEnabled.value!!, _buttonSaveIsEnabled.value!!)
    }

    fun saveFilm(film: FilmEntity) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (!localRepository.isMovieWatched(film.name)){
                        localRepository.insertWatchedMovie(WatchedMoviesMapper.map(film, true))
                    }
                    if (localRepository.isMovieWatched(film.name) && !localRepository.isMovieSaved(film.name)) {
                        localRepository.updateMovieIsSaved(film.name, true)
                    }
                }
            } catch (t: Throwable) { _state.emit(State.ERROR) }
        }
    }

    fun saveFilmAsSeen(film: FilmEntity) {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    if (!localRepository.isMovieWatched(film.name)){
                        localRepository.insertWatchedMovie(WatchedMoviesMapper.map(film, false))
                    }
                    if (localRepository.isMovieWatched(film.name) && localRepository.isMovieSaved(film.name)) {
                        localRepository.updateMovieIsSaved(film.name, false)
                    }
                }
            } catch (t: Throwable) { _state.emit(State.ERROR) }
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

    fun emptyScreenError() {
        viewModelScope.launch {
            _state.emit(State.ERROR)
        }
    }

    private fun queryToTitlesList(query: String): List<String> {
        return query.split("*").toMutableList().take(10)
    }

}