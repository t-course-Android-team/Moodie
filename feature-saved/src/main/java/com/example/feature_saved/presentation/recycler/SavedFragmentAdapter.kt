package com.example.feature_saved.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.feature_saved.databinding.FilmBinding
import com.example.feature_saved.domain.FilmEntity

class SavedFragmentAdapter: ListAdapter<FilmEntity, ViewHolder>(FilmDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FilmBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}