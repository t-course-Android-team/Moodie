package com.example.feature_response.presentation.recycler

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feature_response.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var dataWasSet: Boolean = false
    val name: TextView = view.findViewById(R.id.name)
    val rate: TextView = view.findViewById(R.id.rating)
    val duration: TextView = view.findViewById(R.id.duration)
    val release: TextView = view.findViewById(R.id.release)
    val genres: TextView = view.findViewById(R.id.genres)
    val plot: TextView = view.findViewById(R.id.plot)
    private val poster: ImageView = view.findViewById(R.id.imageView)
    val cardView: CardView = view.findViewById(R.id.cardView)
    val scrollView: ScrollView = view.findViewById(R.id.scroll)
    val seen: Button = view.findViewById(R.id.buttonSeen)
    val save: Button = view.findViewById(R.id.buttonLike)

    fun updatePoster(posterUrl: String) {
        Glide.with(itemView.context).load(posterUrl).into(poster)
    }

}