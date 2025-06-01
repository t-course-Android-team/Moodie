package com.example.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_search.domain.ApiKeyProvider
import com.example.feature_search.domain.SearchMovieUseCase

class FeatureSearchViewModelFactory(
    private val searchMovieUseCase: SearchMovieUseCase, private val apiKeyProvider: ApiKeyProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FeatureSearchViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST") FeatureSearchViewModel(
                    searchMovieUseCase, apiKeyProvider
                ) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}