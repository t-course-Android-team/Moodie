package com.example.feature_saved.presentation.recycler

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.data.WatchedMoviesEntity
import com.example.feature_saved.databinding.FilmBinding

class SavedFragmentAdapter(
    private val deleteMovie: (String) -> Unit,
    private val watchMovie: (String) -> Unit
): ListAdapter<WatchedMoviesEntity, ViewHolder>(FilmDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, deleteMovie, watchMovie)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("SavedFragmentAdapter", "Binding item: $item")
        item?.let {
            holder.bind(it)
        }
    }
}