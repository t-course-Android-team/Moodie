package com.example.feature_search.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_search.domain.ApiKeyProvider
import com.example.feature_search.domain.SearchMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeatureSearchViewModel @Inject constructor(
    private val searchMovieUseCase: SearchMovieUseCase, private val apiKeyProvider: ApiKeyProvider
) : ViewModel() {

    private val _resultMovies = MutableLiveData<String?>()
    val resultMovies: LiveData<String?> = _resultMovies

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    suspend fun searchMovies(prompt: RequestUIModel) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = searchMovieUseCase(prompt)
            _resultMovies.value = result
            _isLoading.value = false
        }
    }
}
