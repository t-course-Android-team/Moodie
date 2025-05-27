package com.example.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_search.domain.SearchMovieUseCase
import javax.inject.Inject

class FeatureSearchViewModelFactory (
private val searchMovieUseCase: SearchMovieUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FeatureSearchViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST") FeatureSearchViewModel(searchMovieUseCase) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}