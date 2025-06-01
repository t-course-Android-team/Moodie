package com.example.feature_saved.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.data.WatchedMoviesEntity

class FilmDiffUtil : DiffUtil.ItemCallback<WatchedMoviesEntity>() {
    override fun areItemsTheSame(
        oldItem: WatchedMoviesEntity,
        newItem: WatchedMoviesEntity
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: WatchedMoviesEntity,
        newItem: WatchedMoviesEntity
    ): Boolean {
        return oldItem == newItem
    }
}