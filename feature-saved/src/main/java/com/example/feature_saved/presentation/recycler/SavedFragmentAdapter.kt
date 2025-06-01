package com.example.feature_saved.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.data.WatchedMoviesEntity
import com.example.feature_saved.databinding.Film1Binding

class SavedFragmentAdapter(
    private val deleteMovie: (String) -> Unit,
    private val watchMovie: (String) -> Unit
) : ListAdapter<WatchedMoviesEntity, ViewHolder>(FilmDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = Film1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, deleteMovie, watchMovie)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}