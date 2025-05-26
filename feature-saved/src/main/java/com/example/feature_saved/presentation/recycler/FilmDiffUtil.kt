package com.example.feature_saved.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.example.feature_saved.domain.FilmEntity

class FilmDiffUtil: DiffUtil.ItemCallback<FilmEntity>() {
    override fun areItemsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean {
        return oldItem == newItem
    }
}