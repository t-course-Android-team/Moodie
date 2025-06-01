package com.example.feature_saved.presentation.recycler

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.WatchedMoviesEntity
import com.example.feature_saved.databinding.Film1Binding

class ViewHolder(
    private val binding: Film1Binding,
    private val deleteMovie: (String) -> Unit,
    private val watchMovie: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WatchedMoviesEntity) = with(binding) {
        name.text = "${item.name}"
        release.text = "${item.yearRelease}"
        duration.text = "${item.duration}"
        genres.text = "${item.genres}"
        updatePoster(item.posterUrl)

        watchedBtn.setOnClickListener {
            watchMovie(name.text.toString())
        }

        deleteBtn.setOnClickListener {
            deleteMovie(name.text.toString())
        }
    }

    private fun updatePoster(posterUrl: String) {
        Glide.with(itemView.context).load(posterUrl).into(binding.image)
    }

}