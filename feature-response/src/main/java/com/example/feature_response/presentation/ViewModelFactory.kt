package com.example.feature_response.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_response.domain.FilmRepository

internal class ViewModelFactory(
    private val repository: FilmRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResponseViewModel(repository) as T
    }
}