package com.example.feature_response.presentation.recycler

import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feature_response.databinding.FilmBinding

class ViewHolder(private val binding: FilmBinding) : RecyclerView.ViewHolder(binding.root) {

    val name: TextView = binding.name
    val rate: TextView = binding.rating
    val duration: TextView = binding.duration
    val release: TextView = binding.release
    val genres: TextView = binding.genres
    val plot: TextView = binding.plot
    private val poster: ImageView = binding.imageView
    val cardView: CardView = binding.cardView
    val scrollView: ScrollView = binding.scroll
    val seen: Button = binding.buttonSeen
    val save: Button = binding.buttonLike

    fun updatePoster(posterUrl: String) {
        Glide.with(itemView.context).load(posterUrl).into(poster)
    }

}