package com.example.feature_saved.presentation.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feature_saved.databinding.FilmBinding
import com.example.feature_saved.domain.FilmEntity

class ViewHolder(private val binding: FilmBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: FilmEntity) = with(binding) {
        name.text = "$item.name"
        updatePoster(item.posterUrl)
    }

    fun updatePoster(posterUrl: String) {
        Glide.with(itemView.context).load(posterUrl).into(binding.image)
    }

}