package com.example.feature_search.presentation

import android.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_search.domain.SearchMovieUseCase
import com.example.feature_search.presentation.UIMappers.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class FeatureSearchViewModel(
    private val searchMovieUseCase: SearchMovieUseCase
) : ViewModel() {
    private val _watchedMovies = MutableLiveData<String?>()
    val watchedMovies: LiveData<String?> = _watchedMovies

   suspend fun searchMovies(prompt: RequestUIModel)  = withContext(Dispatchers.IO){
        viewModelScope.launch {
         val result =   searchMovieUseCase.invoke(prompt.toModel())
        _watchedMovies.value= result
        }
    }


}
