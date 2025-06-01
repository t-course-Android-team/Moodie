package com.example.feature_saved.presentation

sealed class State {
    data class Content(
        val canLoadMore: Boolean = false,
        val canLoadPrevious: Boolean = false,
        val loadingMore: Boolean = false,
        val loadingPrevious: Boolean = false
    ) : State()

    object Loading: State()

    data class Error(
        val message: String,
        val retryAction: (() -> Unit)? = null
    ) : State()
}