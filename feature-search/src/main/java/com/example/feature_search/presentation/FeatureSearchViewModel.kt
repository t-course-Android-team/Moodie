package com.example.feature_search.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_search.domain.SearchMovieUseCase
import com.example.feature_search.presentation.UIMappers.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeatureSearchViewModel(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _watchedMovies = MutableLiveData<String?>()
    val watchedMovies: LiveData<String?> = _watchedMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    suspend fun searchMovies(prompt: RequestUIModel) = withContext(Dispatchers.IO) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = searchMovieUseCase.invoke(prompt.toModel())
            Log.d("FROM AI", result)
            _watchedMovies.value = result
        }
        _isLoading.postValue(false)
    }


}
