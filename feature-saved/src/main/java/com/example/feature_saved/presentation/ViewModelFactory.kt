package com.example.feature_saved.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_saved.domain.FilmRepository

internal class ViewModelFactory(
    private val repository: FilmRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SavedFragmentViewModel(repository) as T
    }
}