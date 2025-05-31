package com.example.feature_response.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.WatchedMoviesRepoImpl
import com.example.feature_response.domain.FilmRepository
import javax.inject.Inject

internal class ViewModelFactory @Inject constructor(
    private val repository: FilmRepository,
    private val localRepository: WatchedMoviesRepoImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResponseViewModel(repository, localRepository) as T
    }
}